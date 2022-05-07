package com.proof.of.concept.repository;

import com.proof.of.concept.model.CommentRelationship;
import com.proof.of.concept.model.EventComments;
import com.proof.of.concept.model.EventNode;
import com.proof.of.concept.model.UserNode;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class NeoRepository {

    @Inject
    SessionFactory sessionFactory;

    private UserNode createUser(String name) {
        Session session = sessionFactory.openSession();
        UserNode userNode = new UserNode(name);
        saveInTransaction(session, userNode);
        return userNode;
    }

    private EventNode createEvent(String id) {
        Session session = sessionFactory.openSession();
        EventNode eventNode = new EventNode(id);
        saveInTransaction(session, eventNode);
        return eventNode;
    }

    public void likeEvent(String userName, String eventId) {
        Session session = sessionFactory.openSession();

        UserNode userNode = session.load(UserNode.class, userName);
        if (userNode == null) {
            userNode = createUser(userName);
        }

        EventNode eventNode = session.load(EventNode.class, eventId);
        if (eventNode == null) {
            eventNode = createEvent(eventId);
        }

        userNode.addLike(eventNode);
        saveInTransaction(session, userNode);
    }

    public void dislikeEvent(String userName, String eventId) {
        Session session = sessionFactory.openSession();

        UserNode userNode = session.load(UserNode.class, userName);
        EventNode eventNode = session.load(EventNode.class, eventId);
        if (userNode == null || eventNode == null) {
            return;
        }

        userNode.removeLike(eventNode);
        saveInTransaction(session, userNode);
    }

    public void commentEvent(String userName, String eventId, String content) {
        Session session = sessionFactory.openSession();

        UserNode userNode = session.load(UserNode.class, userName);
        if (userNode == null) {
            userNode = createUser(userName);
        }

        EventNode eventNode = session.load(EventNode.class, eventId);
        if (eventNode == null) {
            eventNode = createEvent(eventId);
        }

        CommentRelationship comment = new CommentRelationship(content, userNode, eventNode);
        userNode.addComment(comment);

        saveInTransaction(session, userNode);
    }

    public void uncommentEvent(Long commentId) {
        Session session = sessionFactory.openSession();

        CommentRelationship commentRelationship = session.load(CommentRelationship.class, commentId);
        session.delete(commentRelationship);
    }

    public List<EventComments> getAllCommentsForEvent(String eventId) {
        Session session = sessionFactory.openSession();

        Result result = session.query("MATCH (EventNode {id: $eventId})<-[c:comments]-(u:UserNode)\n"
                        + "RETURN ID(c) as id , c.date as date,c.content as content ,u.name as username ORDER BY c.date DESC;",
                Map.of("eventId", eventId));

        List<EventComments> eventComments = new ArrayList<>();
        result.forEach(r -> eventComments.add(new EventComments(r.get("id").toString(), r.get("date").toString(), r.get("content").toString(), r.get("username").toString())));

        return eventComments;
    }

    public List<String> getAllLikesForEvent(String eventId) {
        Session session = sessionFactory.openSession();

        Result result = session.query("MATCH (EventNode {id: $eventId})<-[c:likes]-(u:UserNode) RETURN u.name as username;",
                Map.of("eventId", eventId));

        List<String> likesByUsers = new ArrayList<>();
        result.forEach(r -> likesByUsers.add(r.get("username").toString()));
        return likesByUsers;
    }

    public boolean isEventLikedByUser(String userName, String eventId) {
        Session session = sessionFactory.openSession();

        UserNode userNode = session.load(UserNode.class, userName);
        if (userNode == null) {
            return false;
        }
        return userNode.getLikes().stream().anyMatch(eventNode -> eventNode.getId().equals(eventId));
    }

    private <T> void saveInTransaction(Session session, T node) {
        Transaction transaction = session.beginTransaction();
        try {
            session.save(node);
            transaction.commit();
            transaction.close();

        } catch (RuntimeException e) {
            System.err.println("Could not execute transaction: " + e);
            transaction.rollback();
        }
    }
}

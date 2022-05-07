package com.proof.of.concept.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
@Data
@NoArgsConstructor
public class UserNode {

    @Id
    private String name;

    @Relationship(type = "likes")
    private Set<EventNode> likes = new HashSet<>();

    @Relationship(type = "comments")
    private Set<CommentRelationship> comments = new HashSet<>();

    public UserNode(String name) {
        this.name = name;
    }

    public void addLike(EventNode eventNode) {
        likes.add(eventNode);
    }

    public void removeLike(EventNode eventNode) {
        likes.remove(eventNode);
    }

    public void addComment(CommentRelationship commentRelation) {
        comments.add(commentRelation);
    }
}

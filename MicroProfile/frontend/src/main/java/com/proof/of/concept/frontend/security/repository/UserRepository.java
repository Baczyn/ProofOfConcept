package com.proof.of.concept.frontend.security.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.proof.of.concept.frontend.security.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepository {

    private final static String COLLECTION_NAME = "users";

    @Inject
    private MongoDatabase db;

    public String create(User user) {
        MongoCollection<Document> mongoCollection = db.getCollection(COLLECTION_NAME);
        Document newUser = user.getUserDoc();
        mongoCollection.insertOne(newUser);
        return newUser.toJson();
    }

    public String getById(String userId) {
        MongoCollection<Document> mongoCollection = db.getCollection(COLLECTION_NAME);
        Document query = new Document("_id", new ObjectId(userId));
        Document doc = mongoCollection.find(query).first();
        if (doc != null) {
            String stringId = doc.get("_id").toString();
            doc.remove("_id");
            doc.append("id", stringId);
            return doc.toJson();
        }
        return null;
    }

    public Optional<String> getByName(String name) {
        MongoCollection<Document> mongoCollection = db.getCollection(COLLECTION_NAME);
        Document query = new Document("name", name);
        Document doc = mongoCollection.find(query).first();
        System.out.println("GetByName "+doc);
        if (doc != null) {
            String stringId = doc.get("_id").toString();
            doc.remove("_id");
            doc.append("id", stringId);
            return Optional.of(doc.toJson());
        }
        return Optional.empty();
    }

    public String getAll() {

        List<String> events = new ArrayList<>();
        MongoCollection<Document> eventCollection = db.getCollection(COLLECTION_NAME);
        FindIterable<Document> docs = eventCollection.find();
        for (Document doc : docs) {
            String stringId = doc.get("_id").toString();
            doc.remove("_id");
            doc.append("id", stringId);
            events.add(doc.toJson());
        }
        return "[" + String.join(",", events) + "]";
    }



    public String deleteById(String userId) {
        MongoCollection<Document> mongoCollection = db.getCollection(COLLECTION_NAME);
        Document query = new Document("_id", new ObjectId(userId));
        Document doc = mongoCollection.findOneAndDelete(query);
        if (doc != null) {
            return doc.toJson();
        }
        return null;
    }

//    public String updateById(String id, Event event) {
//
//        MongoCollection<Document> eventCollection = db.getCollection(COLLECTION_NAME);
//        Document query = new Document("_id", new ObjectId(id));
//        Document doc = eventCollection.findOneAndReplace(query, event.getEventDoc());
//        if (doc != null) {
//            return doc.toJson();
//        }
//        return null;
//    }
//

}

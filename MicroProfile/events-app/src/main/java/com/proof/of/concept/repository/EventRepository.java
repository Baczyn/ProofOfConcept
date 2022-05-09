package com.proof.of.concept.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.proof.of.concept.model.Event;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EventRepository {

    private final static String COLLECTION_NAME = "Events";

    @Inject
    private MongoDatabase db;

    public String add(Event event) {

        MongoCollection<Document> eventCollection = db.getCollection(COLLECTION_NAME);
        Document newEvent = event.getEventDoc();
        eventCollection.insertOne(newEvent);
        return newEvent.toJson();
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

    public String getById(String id) {

        MongoCollection<Document> eventCollection = db.getCollection(COLLECTION_NAME);
        Document query = new Document("_id", new ObjectId(id));
        Document doc = eventCollection.find(query).first();

        if (doc != null) {
            String stringId = doc.get("_id").toString();
            doc.remove("_id");
            doc.append("id", stringId);
            return doc.toJson();
        }
        return null;
    }

    public String deleteById(String id) {

        MongoCollection<Document> eventCollection = db.getCollection(COLLECTION_NAME);
        Document query = new Document("_id", new ObjectId(id));
        Document doc = eventCollection.findOneAndDelete(query);
        if (doc != null) {
            return doc.toJson();
        }
        return null;
    }

    public String updateById(String id, Event event) {

        MongoCollection<Document> eventCollection = db.getCollection(COLLECTION_NAME);
        Document query = new Document("_id", new ObjectId(id));
        Document doc = eventCollection.findOneAndReplace(query, event.getEventDoc());
        if (doc != null) {
            return doc.toJson();
        }
        return null;
    }
}

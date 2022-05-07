package com.proof.of.concept.model;

import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.time.LocalDateTime;

@RelationshipEntity(type = "comments")
@NoArgsConstructor
public class CommentRelationship {

    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String content;

    @Property
    private LocalDateTime date;

    @StartNode
    private UserNode userNode;

    @EndNode
    private EventNode movie;

    public CommentRelationship(String content, UserNode userNode, EventNode movie) {
        this.date = LocalDateTime.now();
        this.content = content;
        this.userNode = userNode;
        this.movie = movie;
    }
}

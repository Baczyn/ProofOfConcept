package com.proof.of.concept.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class EventNode {

    @Id
    private String id;
}

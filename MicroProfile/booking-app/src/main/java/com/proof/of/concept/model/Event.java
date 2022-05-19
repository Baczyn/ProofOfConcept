package com.proof.of.concept.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Event")
@NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e")
public class Event implements Serializable {

    @Id
    private String eventId;

    private Integer numberOfTickets;

    public Event(EventRequest eventRequest) {
        this.eventId = eventRequest.getEventId();
        this.numberOfTickets = eventRequest.getNumberOfTickets();
    }
}

package com.proof.of.concept.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Event")
@NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e")
public class Event implements Serializable {

    @Id
    private Integer eventId;

    private Integer numberOfTickets;

    private Timestamp startingDateOfBooking;

    public Event(EventRequest eventRequest){
        this.eventId = eventRequest.getEventId();
        this.numberOfTickets = eventRequest.getNumberOfTickets();
        this.startingDateOfBooking = Timestamp.valueOf(eventRequest.getStartingDateOfBooking());
    }

}

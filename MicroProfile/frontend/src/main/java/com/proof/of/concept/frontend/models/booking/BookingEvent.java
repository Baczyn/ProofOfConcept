package com.proof.of.concept.frontend.models.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingEvent {

    private String eventId;
    private Integer numberOfTickets;
}


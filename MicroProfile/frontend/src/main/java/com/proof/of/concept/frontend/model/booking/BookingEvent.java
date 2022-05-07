package com.proof.of.concept.frontend.model.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingEvent {

    private String eventId;
    private Integer numberOfTickets;
}


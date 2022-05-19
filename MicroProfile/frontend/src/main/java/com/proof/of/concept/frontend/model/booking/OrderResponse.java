package com.proof.of.concept.frontend.model.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Integer id;
    private String username;
    private BookingEvent event;
    private Integer ticketQuantity;
    private Timestamp orderedAt;
}

package com.proof.of.concept.frontend.models.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Integer id;
    private Integer userId;
    private BookingEvent event;
    private Integer ticketQuantity;
    private Timestamp orderedAt;
}

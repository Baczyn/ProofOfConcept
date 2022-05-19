package com.proof.of.concept.frontend.model.booking;

import com.proof.of.concept.frontend.model.event.EventResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFullResponse {

    private Integer id;
    private String username;
    private EventResponse event;
    private Integer ticketQuantity;
    private Timestamp orderedAt;

    public OrderFullResponse(OrderResponse orderResponse, EventResponse eventResponse) {
        id = orderResponse.getId();
        username = orderResponse.getUsername();
        ticketQuantity = orderResponse.getTicketQuantity();
        orderedAt = orderResponse.getOrderedAt();
        event = eventResponse;
    }
}

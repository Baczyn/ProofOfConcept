package com.proof.of.concept.frontend.models.booking;

import com.proof.of.concept.frontend.models.event.EventResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFullResponse {

    private Integer id;
    private Integer userId;
    private EventResponse event;
    private Integer ticketQuantity;
    private Timestamp orderedAt;

    public OrderFullResponse(OrderResponse orderResponse, EventResponse eventResponse) {
        id=orderResponse.getId();
        userId = orderResponse.getUserId();
        ticketQuantity = orderResponse.getTicketQuantity();
        orderedAt = orderResponse.getOrderedAt();
        event = eventResponse;
    }
}

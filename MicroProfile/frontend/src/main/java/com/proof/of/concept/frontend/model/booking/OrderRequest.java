package com.proof.of.concept.frontend.model.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private String eventId;
    private Integer ticketQuantity;
    private String userName;
    private String deliveryMethod;
    private String paymentMethod;
}

package com.proof.of.concept.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderRequest {

    @NotBlank(message = "Order must have a event id!")
    private String eventId;

    @NotBlank(message = "Order must have a user!")
    private String userName;

    @Positive(message = "Order must have a ticket quantity!")
    private Integer ticketQuantity;

    private String deliveryMethod;

    private String paymentMethod;

}

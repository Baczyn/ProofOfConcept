package com.proof.of.concept.model;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderRequest {

    @NotBlank(message = "Order must have a event id!")
    private String eventId;

    @Positive(message = "Order must have a ticket quantity!")
    private Integer ticketQuantity;

    private String deliveryMethod;

    private String paymentMethod;

}

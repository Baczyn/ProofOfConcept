package com.proof.of.concept.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OrderTable")
@NamedQuery(name = "Order.findAll", query = "SELECT e FROM Order e")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Event event;


    private Integer ticketQuantity;

    private Timestamp orderedAt;

    private String deliveryMethod;

    private String paymentMethod;

    public Order(Integer userId, Event event, Integer ticketQuantity, Timestamp orderedAt, String deliveryMethod, String paymentMethod) {
        this.userId = userId;
        this.event = event;
        this.ticketQuantity = ticketQuantity;
        this.orderedAt = orderedAt;
        this.deliveryMethod = deliveryMethod;
        this.paymentMethod = paymentMethod;
    }

    public Order(OrderRequest orderRequest){
        this.ticketQuantity = orderRequest.getTicketQuantity();
        this.orderedAt = new Timestamp(System.currentTimeMillis());
        this.deliveryMethod = orderRequest.getDeliveryMethod();
        this.paymentMethod = orderRequest.getPaymentMethod();
        this.event = new Event();
        this.event.setEventId(orderRequest.getEventId());

    }
}

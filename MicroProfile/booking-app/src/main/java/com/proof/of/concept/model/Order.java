package com.proof.of.concept.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OrderTable")
@NamedQuery(name = "Order.findAll", query = "SELECT e FROM Order e")
@NamedQuery(name = "Order.findByUserName", query = "SELECT e FROM Order e WHERE e.username = :username")
public class Order implements Serializable {

    @Id
    private Integer id = IdGenerator.generateUniqueId();

    private String username;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Event event;

    private Integer ticketQuantity;

    private Timestamp orderedAt;

    private String deliveryMethod;

    private String paymentMethod;

    public Order(OrderRequest orderRequest) {
        this.username = orderRequest.getUsername();
        this.ticketQuantity = orderRequest.getTicketQuantity();
        this.orderedAt = new Timestamp(System.currentTimeMillis());
        this.deliveryMethod = orderRequest.getDeliveryMethod();
        this.paymentMethod = orderRequest.getPaymentMethod();
        this.event = new Event();
        this.event.setEventId(orderRequest.getEventId());
    }
}

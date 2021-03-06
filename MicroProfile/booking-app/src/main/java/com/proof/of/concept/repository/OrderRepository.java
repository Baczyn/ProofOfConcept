package com.proof.of.concept.repository;

import com.proof.of.concept.exceptions.NumberOfTicketException;
import com.proof.of.concept.model.Event;
import com.proof.of.concept.model.Order;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class OrderRepository {

    @PersistenceContext(name = "jpa-unit")
    private EntityManager entityManager;

    @Inject
    private EventRepository eventRepository;


    public Order create(Order order) throws NumberOfTicketException {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Event event = entityManager.getReference(Event.class, order.getEvent().getEventId());
        event = eventRepository.updateNumberOfTickets(event, order.getTicketQuantity());
        order.setEvent(event);
        entityManager.persist(order);
        return order;
    }

    public Order delete(int orderId) throws NumberOfTicketException {
        Order order = entityManager.find(Order.class, orderId);
        if (order != null) {
            eventRepository.updateNumberOfTickets(order.getEvent(), -order.getTicketQuantity());
            entityManager.remove(order);
        }
        return order;
    }

    public Order update(Order order) {
        return entityManager.merge(order);
    }

    public List<Order> getAll() {
        return entityManager.createNamedQuery("Order.findAll", Order.class).getResultList();
    }

    public List<Order> findByUsername(String username) {
        return entityManager.createNamedQuery("Order.findByUserName", Order.class).setParameter("username", username).getResultList();
    }
}

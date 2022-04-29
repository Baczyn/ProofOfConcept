package com.proof.of.concept.repository;

import com.proof.of.concept.exceptions.NumberOfTicketException;
import com.proof.of.concept.model.Event;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
@Transactional
public class EventRepository {

    @PersistenceContext(name = "jpa-unit")
    private EntityManager entityManager;

    public Event create(Event event) {
        entityManager.persist(event);
        return event;
    }

    public Event delete(Integer eventId) {
        Event event = findById(eventId);
        if (event != null) {
            entityManager.remove(event);
        }
        return event;
    }

    public Event update(Event event) {
        return entityManager.merge(event);
    }

    public Event updateNumberOfTickets(Event event, Integer numberOfReservedTickets) throws NumberOfTicketException {
        int freeTickets = event.getNumberOfTickets() - numberOfReservedTickets;
        if (freeTickets <= 0) {
            throw new NumberOfTicketException(event.getNumberOfTickets());
        }
        event.setNumberOfTickets(freeTickets);
        entityManager.merge(event);
        return event;
    }

    public List<Event> getAll() {
        return entityManager.createNamedQuery("Event.findAll", Event.class).getResultList();
    }

    public Event findById(Integer eventId) {
        return entityManager.find(Event.class, eventId);
    }

}

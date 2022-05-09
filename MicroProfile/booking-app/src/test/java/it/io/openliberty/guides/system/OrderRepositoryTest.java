package it.io.openliberty.guides.system;

import com.proof.of.concept.model.Event;
import com.proof.of.concept.model.Order;
import com.proof.of.concept.repository.OrderRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderRepositoryTest {

    @Mock
    private OrderRepository orderRepository;

    private static final Integer userId = 1;

    private static final Integer numberOfTickets = 50;

    private static Order order;

    private static Event event;

    @BeforeAll
    private static void create() throws Exception {

//        event = new Event(1, numberOfTickets, new Timestamp(100));
//        order = new Order("userId", event, 4, new Timestamp(123), "asd", "asd");

    }

//
//    @Test
//    void createOrderTest() {
//        Order newOrder = orderRepository.create(order);
//        assertEquals(order, newOrder);
//
//    }
}

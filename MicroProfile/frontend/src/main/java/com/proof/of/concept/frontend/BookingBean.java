package com.proof.of.concept.frontend;

import com.proof.of.concept.frontend.client.BookingEventClient;
import com.proof.of.concept.frontend.client.BookingOrderClient;
import com.proof.of.concept.frontend.client.EventClient;
import com.proof.of.concept.frontend.models.booking.BookingEvent;
import com.proof.of.concept.frontend.models.booking.OrderFullResponse;
import com.proof.of.concept.frontend.models.booking.OrderRequest;
import com.proof.of.concept.frontend.models.booking.OrderResponse;
import com.proof.of.concept.frontend.models.event.EventResponse;
import com.proof.of.concept.frontend.util.SessionUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.json.JsonArray;
import jakarta.json.JsonValue;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Named
public class BookingBean {

    @Inject
    @RestClient
    private BookingEventClient bookingEventClient;

    @Inject
    @RestClient
    private BookingOrderClient bookingOrderClient;

    @Inject
    @RestClient
    private EventClient eventClient;

    public BookingEvent doGetNoOfTickets(String eventId) {
        String jwt = SessionUtils.getJwtToken();
        try {
            Response response = bookingEventClient.getById(jwt, eventId);
            return response.readEntity(BookingEvent.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new BookingEvent(eventId,0);
    }

    public String doUpdateNoOfTickets(String eventId, Integer numberOfTickets) {
        BookingEvent request = new BookingEvent(eventId, numberOfTickets);
        String jwt = SessionUtils.getJwtToken();
        try {
            bookingEventClient.update(jwt, request);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "eventDetails.jsf?faces-redirect=true&id=" + eventId;
    }

    public String doCreateOrder(String eventId, String ticketQuantity ) {
        String jwt = SessionUtils.getJwtToken();
        System.out.println("CreateOrder" + eventId);

        OrderRequest orderRequest = new OrderRequest(eventId,Integer.valueOf(ticketQuantity),null,null);
        System.out.println("CreateOrder" + orderRequest+"strinh"+ticketQuantity);
        try{
            bookingOrderClient.create(jwt, orderRequest);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "eventDetails.jsf?faces-redirect=true&id=" + eventId;
    }

    public List<OrderFullResponse> doGetOrders() {
        String jwt = SessionUtils.getJwtToken();
        List<OrderFullResponse> list = new ArrayList<>();
        try {
            JsonArray array = bookingOrderClient.getByUserId(jwt,1).readEntity(JsonArray.class);
            for (JsonValue item : array) {
                OrderResponse orderResponse = JsonbBuilder.create().fromJson(item.toString(), OrderResponse.class);
                EventResponse response = eventClient.getById(orderResponse.getEvent().getEventId()).readEntity(EventResponse.class);
                list.add(new OrderFullResponse(orderResponse,response));
            }
            return list;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String doRemoveOrder(String orderId) {
        String jwt = SessionUtils.getJwtToken();
        bookingOrderClient.delete(jwt, Integer.valueOf(orderId));
        return "orderDetails.jsf?faces-redirect=true";
    }
}

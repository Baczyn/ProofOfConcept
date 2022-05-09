package com.proof.of.concept.frontend;

import com.proof.of.concept.frontend.client.BookingEventClient;
import com.proof.of.concept.frontend.client.BookingOrderClient;
import com.proof.of.concept.frontend.client.EventClient;
import com.proof.of.concept.frontend.model.booking.BookingEvent;
import com.proof.of.concept.frontend.model.booking.OrderFullResponse;
import com.proof.of.concept.frontend.model.booking.OrderRequest;
import com.proof.of.concept.frontend.model.booking.OrderResponse;
import com.proof.of.concept.frontend.model.event.EventResponse;
import com.proof.of.concept.frontend.util.SessionUtils;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.json.JsonArray;
import jakarta.json.JsonValue;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.core.Response;
import lombok.Data;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ConversationScoped
@Named
@Data
public class BookingBean implements Serializable {

    @Serial
    private static final long serialVersionUID = 4771270804699990999L;

    @Inject
    private Conversation conversation;

    @Inject
    @RestClient
    private BookingEventClient bookingEventClient;

    @Inject
    @RestClient
    private BookingOrderClient bookingOrderClient;

    @Inject
    @RestClient
    private EventClient eventClient;

    private String eventId;
    private Integer availableNoOfTickets;

    public void initConversation() {
        if (!FacesContext.getCurrentInstance().isPostback()
                && conversation.isTransient()) {
            conversation.begin();
            System.out.println("initConversation");
        }
    }

    @PostConstruct
    private void setIdFromParam() {
        this.eventId = SessionUtils.getEventIdFormParam();
        this.availableNoOfTickets = doGetNoOfTickets();
    }

    private Integer doGetNoOfTickets() {
        String jwt = SessionUtils.getJwtToken();
        try {
            Response response = bookingEventClient.getById(jwt, this.eventId);
            return response.readEntity(BookingEvent.class).getNumberOfTickets();
        } catch (Exception e) {
            return 0;
        }
    }

    public String doUpdateNoOfTickets() {
        String jwt = SessionUtils.getJwtToken();
        BookingEvent request = new BookingEvent(this.eventId, this.availableNoOfTickets);
        try {
            bookingEventClient.update(jwt, request);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "eventDetails.jsf?faces-redirect=true&id=" + this.eventId;
    }

    public String doCreateOrder(String ticketQuantity) {
        String jwt = SessionUtils.getJwtToken();
        String username = SessionUtils.getCurrentUserName();

        OrderRequest orderRequest = new OrderRequest(this.eventId, Integer.valueOf(ticketQuantity), username, null, null);

        try {
            bookingOrderClient.create(jwt, orderRequest);
            availableNoOfTickets -= Integer.parseInt(ticketQuantity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "eventDetails.jsf?faces-redirect=true&id=" + this.eventId;
    }

    public List<OrderFullResponse> doGetOrders() {
        String jwt = SessionUtils.getJwtToken();
        String username = SessionUtils.getCurrentUserName();
        List<OrderFullResponse> list = new ArrayList<>();

        try {
            JsonArray array = bookingOrderClient.getByUserId(jwt, username).readEntity(JsonArray.class);
            for (JsonValue item : array) {
                OrderResponse orderResponse = JsonbBuilder.create().fromJson(item.toString(), OrderResponse.class);
                try {
                    EventResponse response = eventClient.getById(orderResponse.getEvent().getEventId()).readEntity(EventResponse.class);
                    list.add(new OrderFullResponse(orderResponse, response));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String doRemoveOrder(String orderId) {
        String jwt = SessionUtils.getJwtToken();
        bookingOrderClient.delete(jwt, Integer.valueOf(orderId));
        return "orderDetails.jsf?faces-redirect=true";
    }
}

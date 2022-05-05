package com.proof.of.concept.frontend;

import com.proof.of.concept.frontend.client.BookingEventClient;
import com.proof.of.concept.frontend.client.EventClient;
import com.proof.of.concept.frontend.models.event.EventResponse;
import com.proof.of.concept.frontend.util.SessionUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.json.JsonArray;
import jakarta.json.JsonValue;
import jakarta.json.bind.JsonbBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Named
public class ApplicationBean {

    @Inject
    @RestClient
    private EventClient eventClient;

    public List<EventResponse> getAll() {
        JsonArray array = eventClient.getAll().readEntity(JsonArray.class);
        List<EventResponse> list = new ArrayList<>();
        for (JsonValue item : array) {
            list.add(JsonbBuilder.create().fromJson(item.toString(), EventResponse.class));
        }
        return list;
    }

    public String doView(String eventId) {
        return "eventDetails.jsf?faces-redirect=true&id=" + eventId;
    }

    public String doRemoveEvent(String eventId) {
        eventClient.remove(SessionUtils.getJwtToken(), eventId);
        return "index.jsf?faces-redirect=true";
    }
}

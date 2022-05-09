package com.proof.of.concept.frontend;

import com.proof.of.concept.frontend.client.EventClient;
import com.proof.of.concept.frontend.model.event.EventRequest;
import com.proof.of.concept.frontend.model.event.EventResponse;
import com.proof.of.concept.frontend.model.event.Location;
import com.proof.of.concept.frontend.util.SessionUtils;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.json.JsonArray;
import jakarta.json.JsonValue;
import jakarta.json.bind.JsonbBuilder;
import lombok.Data;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequestScoped
@Named
@Data
public class EventBean {

    @Inject
    @RestClient
    private EventClient eventClient;

    private String title;
    private String date;
    private String country;
    private String city;
    private String streetName;
    private String streetNumber;
    private String facilityName;
    private String id;
    private String description;

    @PostConstruct
    private void setIdFromParam() {
        this.id = SessionUtils.getEventIdFormParam();
    }

    public List<EventResponse> getAll() {
        JsonArray array = eventClient.getAll().readEntity(JsonArray.class);
        List<EventResponse> list = new ArrayList<>();
        for (JsonValue item : array) {
            list.add(JsonbBuilder.create().fromJson(item.toString(), EventResponse.class));
        }
        return list;
    }

    public String doRemoveEvent(String eventId) {
        eventClient.remove(SessionUtils.getJwtToken(), eventId);
        return "index.jsf?faces-redirect=true";
    }

    public String doView(String eventId) {
        return "eventDetails.jsf?faces-redirect=true&id=" + eventId;
    }

    public String doAddEvent() throws Exception {
        String jwt = SessionUtils.getJwtToken();
        EventRequest eventRequest = new EventRequest(title, new Location(country, city, streetName, streetNumber, facilityName), description, date);
        if (Objects.equals(this.id, "")) {
            eventClient.add(jwt, eventRequest);
        } else {
            eventClient.update(jwt, eventRequest, this.id);
        }
        return "index.jsf?faces-redirect=true";
    }

    public EventResponse doGetById() throws Exception {
        if (!this.id.equals("")) {
            try {
                return eventClient.getById(id).readEntity(EventResponse.class);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return new EventResponse();
    }

    public void fillEventFormById(String eventId) {
        if (!eventId.equals("")) {
            EventResponse eventResponse = eventClient.getById(eventId).readEntity(EventResponse.class);
            title = eventResponse.getTitle();
            date = eventResponse.getDate().toString();
            country = eventResponse.getLocation().getCountry();
            description = eventResponse.getDescription();
            city = eventResponse.getLocation().getCity();
            streetName = eventResponse.getLocation().getStreetName();
            streetNumber = eventResponse.getLocation().getStreetNumber();
            facilityName = eventResponse.getLocation().getFacilityName();
            id = eventResponse.getId();
        }
    }
}

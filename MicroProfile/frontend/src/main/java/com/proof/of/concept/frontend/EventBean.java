package com.proof.of.concept.frontend;

import com.proof.of.concept.frontend.client.EventClient;
import com.proof.of.concept.frontend.models.event.EventRequest;
import com.proof.of.concept.frontend.models.event.EventResponse;
import com.proof.of.concept.frontend.models.event.Location;
import com.proof.of.concept.frontend.util.SessionUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
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

    public String doAddEvent() throws Exception {
        String jwt = SessionUtils.getJwtToken();
        EventRequest eventRequest = new EventRequest(title, new Location(country, city, streetName, streetNumber, facilityName),description, date);
        if (id == null) {
            eventClient.add(jwt, eventRequest);
        } else {
            eventClient.update(jwt, eventRequest, id);
        }

        return "index.jsf";
    }

    public EventResponse doGetById(String id) throws Exception {
        return eventClient.getById(id).readEntity(EventResponse.class);
    }

    public String doEditEvent(String eventId) throws Exception {
        EventResponse eventResponse = eventClient.getById(eventId).readEntity(EventResponse.class);
        title = eventResponse.getTitle();
        date = eventResponse.getDate().toString();
        country = eventResponse.getLocation().getCountry();
        city = eventResponse.getLocation().getCity();
        streetName = eventResponse.getLocation().getStreetName();
        streetNumber = eventResponse.getLocation().getStreetNumber();
        facilityName = eventResponse.getLocation().getFacilityName();
        id = eventResponse.getId();

        return "eventForm.jsf?faces-redirect=true";
    }
}


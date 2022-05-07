package com.proof.of.concept.frontend.client;

import com.proof.of.concept.frontend.model.booking.BookingEvent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://localhost:8080/booking-app")
@Path("/event")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface BookingEventClient extends AutoCloseable {

    @GET
    @Path("{id}")
    Response getById(@HeaderParam("Authorization") String authHeader, @PathParam("id") String id);

    @PUT
    Response update(@HeaderParam("Authorization") String authHeader, BookingEvent request);

}

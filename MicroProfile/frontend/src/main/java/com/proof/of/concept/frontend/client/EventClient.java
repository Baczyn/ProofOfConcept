package com.proof.of.concept.frontend.client;

import com.proof.of.concept.frontend.models.event.EventRequest;
import com.proof.of.concept.frontend.models.event.EventResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://localhost:9080/event-app")
@Path("/event")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface EventClient extends AutoCloseable {

    @GET
    Response getAll();

    @GET
    @Path("/{id}")
    Response getById(@PathParam("id") String id);

    @POST
    Response add(@HeaderParam("Authorization") String authHeader, EventRequest event);

    @PUT
    @Path("/{id}")
    Response update(@HeaderParam("Authorization") String authHeader,EventRequest eventRequest, @PathParam("id") String id);

    @DELETE
    @Path("/{id}")
    Response remove(@HeaderParam("Authorization") String authHeader,@PathParam("id") String id);
}

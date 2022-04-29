package com.proof.of.concept.frontend.client;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.HeaderParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://localhost:8080/booking-app")
@Path("/booking")
@RequestScoped
public interface BookingClient extends AutoCloseable {

//    @GET
//    @Path("/os")
//    @Produces(MediaType.APPLICATION_JSON)
//    String getOS(@HeaderParam("Authorization") String authHeader);

    @GET
    @Path("/username")
    @Produces(MediaType.APPLICATION_JSON)
    String getUsername(@HeaderParam("Authorization") String authHeader);

    @GET
    @Path("/jwtroles")
    @Produces(MediaType.APPLICATION_JSON)
    String getJwtRoles(@HeaderParam("Authorization") String authHeader);
}

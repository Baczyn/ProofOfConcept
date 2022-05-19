package com.proof.of.concept.frontend.client;

import com.proof.of.concept.frontend.model.booking.OrderRequest;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.concurrent.CompletionStage;

@RegisterRestClient(baseUri = "http://localhost:9081/booking-app")
@Path("/order")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface BookingOrderClient extends AutoCloseable {

    @POST
    Response create(@HeaderParam("Authorization") String authHeader, OrderRequest orderRequest);

    @POST
    @Path("async")
    CompletionStage<Response> createAsync(@HeaderParam("Authorization") String authHeader, OrderRequest orderRequest);

    @GET
    @Path("async/{username}")
    Response getTasksByUsername(@HeaderParam("Authorization") String authHeader, @PathParam("username") String username);

    @GET
    @Path("{username}")
    Response getByUserId(@HeaderParam("Authorization") String authHeader, @PathParam("username") String username);

    @DELETE
    @Path("{id}")
    Response delete(@HeaderParam("Authorization") String authHeader, @PathParam("id") Integer orderId);
}

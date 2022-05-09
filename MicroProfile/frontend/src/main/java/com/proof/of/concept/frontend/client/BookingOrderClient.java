package com.proof.of.concept.frontend.client;

import com.proof.of.concept.frontend.model.booking.OrderRequest;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://localhost:9081/booking-app")
@Path("/order")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface BookingOrderClient extends AutoCloseable {

    @POST
    Response create(@HeaderParam("Authorization") String authHeader, OrderRequest orderRequest);

    @GET
    @Path("{userName}")
    Response getByUserId(@HeaderParam("Authorization") String authHeader, @PathParam("userName") String userName);

    @DELETE
    @Path("{id}")
    Response delete(@HeaderParam("Authorization") String authHeader, @PathParam("id") Integer orderId);
}

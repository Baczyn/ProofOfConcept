package com.proof.of.concept.rest;

import com.proof.of.concept.exceptions.NumberOfTicketException;
import com.proof.of.concept.model.Order;
import com.proof.of.concept.model.OrderRequest;
import com.proof.of.concept.repository.OrderRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.util.List;

@RequestScoped
@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderRepository orderRepository;

    @GET
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully listed the orders."),
            @APIResponse(
                    responseCode = "500",
                    description = "Failed to list the orders.")})
    @Operation(summary = "List the orders from the database.")
    public Response getAll() {
        List<Order> orderList;
        try{
            orderList = orderRepository.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(orderList)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @POST
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully added orders."),
            @APIResponse(
                    responseCode = "404",
                    description = "Invalid orders configuration."),
            @APIResponse(
                    responseCode = "400",
                    description = "Not enough tickets.")})

    @Operation(summary = "Add a new orders to the database.")
    public Response create(OrderRequest orderRequest) {
        Order newOrder;
        try {
            Order order = new Order(orderRequest);
            order.setUserId(1);
            newOrder = orderRepository.create(order);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (NumberOfTicketException e) {
            System.out.println(e.getMessage());
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(newOrder)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @DELETE
    @Path("{id}")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully deleted order."),
            @APIResponse(
                    responseCode = "400",
                    description = "Order object id was not found.")})
    @Operation(summary = "Delete the order from the database.")
    public Response delete(@PathParam("id") Integer eventId) {
        Order order = orderRepository.delete(eventId);
        return Response
                .status(order == null ? Response.Status.NOT_FOUND : Response.Status.OK)
                .entity(order)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @PUT
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully updated order."),
            @APIResponse(
                    responseCode = "400",
                    description = "Order object id was not found.")})
    @Operation(summary = "Update the order in the database.")
    public Response update(Order order) {
        Order updatedOrder = orderRepository.update(order);
        return Response
                .status(updatedOrder == null ? Response.Status.NOT_FOUND : Response.Status.OK)
                .entity(updatedOrder)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}

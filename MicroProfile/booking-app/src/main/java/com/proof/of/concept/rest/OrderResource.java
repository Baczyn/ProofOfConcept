package com.proof.of.concept.rest;

import com.proof.of.concept.async.AsyncTasksStateHolder;
import com.proof.of.concept.async.Task;
import com.proof.of.concept.async.TaskState;
import com.proof.of.concept.exceptions.NumberOfTicketException;
import com.proof.of.concept.model.Order;
import com.proof.of.concept.model.OrderRequest;
import com.proof.of.concept.model.TaskResponse;
import com.proof.of.concept.repository.OrderRepository;
import jakarta.annotation.Resource;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.concurrent.Asynchronous;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.container.Suspended;
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
    @Inject
    AsyncTasksStateHolder asyncTasksStateHolder;
    @Resource
    private ManagedExecutorService executor;

    @GET
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully listed the orders."),
            @APIResponse(
                    responseCode = "500",
                    description = "Failed to list the orders.")})
    @Operation(summary = "List the orders from the database.")
    @RolesAllowed({"ADMIN"})
    public Response getAll() {
        List<Order> orderList;
        try {
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

    @GET
    @Path("{username}")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully listed user's orders."),
            @APIResponse(
                    responseCode = "500",
                    description = "Failed to list user's orders.")})
    @Operation(summary = "List user's orders from the database.")
    @RolesAllowed({"ADMIN", "USER"})
    public Response getByUsername(@PathParam("username") String username) {
        List<Order> orderList;
        try {
            orderList = orderRepository.findByUsername(username);
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
    @RolesAllowed({"ADMIN", "USER"})
    public Response create(OrderRequest orderRequest) {
        Order newOrder;
        try {
            Order order = new Order(orderRequest);
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

    @POST
    @Path("async")
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

    @Operation(summary = "Add a new orders to the database asynchronously.")
    @RolesAllowed({"ADMIN", "USER"})
    public void createAsync(OrderRequest orderRequest, @Suspended AsyncResponse response) {

        Order order = new Order(orderRequest);

        Task task = asyncTasksStateHolder.initTask(order);

        executor.execute(() -> {
            Response httpResponse;
            try {
                task.updateState(TaskState.IN_PROGRESS);
                orderRepository.create(order);
                task.updateState(TaskState.DONE);
                httpResponse = Response.status(Response.Status.CREATED).entity(order).build();

            } catch (EntityNotFoundException e) {
                task.updateState(TaskState.FAILED);
                httpResponse = Response.status(Response.Status.NOT_FOUND).build();

            } catch (NumberOfTicketException e) {
                task.updateState(TaskState.FAILED);
                httpResponse = Response.status(Response.Status.BAD_REQUEST).build();
            }

            response.resume(httpResponse);
        });
    }


    @POST
    @Path("v2/async")
    @Asynchronous
    @RolesAllowed({"ADMIN", "USER"})
    public void createAsyncV2(OrderRequest orderRequest, @Suspended AsyncResponse response) {

        Order order = new Order(orderRequest);

        Task task = asyncTasksStateHolder.initTask(order);

        Response httpResponse;

        try {
            task.updateState(TaskState.IN_PROGRESS);
            orderRepository.create(order);
            task.updateState(TaskState.DONE);
            httpResponse = Response.status(Response.Status.CREATED).entity(order).build();

        } catch (EntityNotFoundException e) {
            task.updateState(TaskState.FAILED);
            httpResponse = Response.status(Response.Status.NOT_FOUND).build();

        } catch (NumberOfTicketException e) {
            task.updateState(TaskState.FAILED);
            httpResponse = Response.status(Response.Status.BAD_REQUEST).build();
        }

        response.resume(httpResponse);
    }

    @GET
    @Path("/async/{username}")
    @RolesAllowed({"ADMIN", "USER"})
    public Response getTasksByUsername(@PathParam("username") String username) {

        List<TaskResponse> taskList = asyncTasksStateHolder.getTasksByUsername(username)
                .stream()
                .map(TaskResponse::new)
                .toList();

        return Response
                .status(Response.Status.OK)
                .entity(taskList)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @GET
    @Path("/async/all")
    @RolesAllowed({"ADMIN", "USER"})
    public Response getTasks() {

        List<TaskResponse> taskList = asyncTasksStateHolder.getTasks()
                .stream()
                .map(TaskResponse::new)
                .toList();

        return Response
                .status(Response.Status.OK)
                .entity(taskList)
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
    @RolesAllowed({"ADMIN", "USER"})
    public Response delete(@PathParam("id") Integer orderId) {
        Order order;
        try {
            order = orderRepository.delete(orderId);
        } catch (NumberOfTicketException e) {
            System.out.println(e.getMessage());
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
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
    @RolesAllowed({"ADMIN", "USER"})
    public Response update(Order order) {
        Order updatedOrder = orderRepository.update(order);
        return Response
                .status(updatedOrder == null ? Response.Status.NOT_FOUND : Response.Status.OK)
                .entity(updatedOrder)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}

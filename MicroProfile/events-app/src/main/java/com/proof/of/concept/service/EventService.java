package com.proof.of.concept.service;

import com.proof.of.concept.model.Event;
import com.proof.of.concept.repository.EventRepository;
import com.proof.of.concept.validator.EventValidator;
import jakarta.inject.Inject;
import jakarta.json.JsonArray;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;


@Path("/event")
public class EventService {

    @Inject
    EventRepository eventRepository;

    @Inject
    EventValidator validator;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully added event."),
            @APIResponse(
                    responseCode = "400",
                    description = "Invalid event configuration.")})
    @Operation(summary = "Add a new event to the database.")
    public Response add(Event event) {
        JsonArray violations = validator.getViolations(event);

        if (!violations.isEmpty()) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(violations.toString())
                    .build();
        }

        String newEvent = eventRepository.add(event);

        return Response
                .status(Response.Status.OK)
                .entity(newEvent)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully listed the events."),
            @APIResponse(
                    responseCode = "500",
                    description = "Failed to list the events.")})
    @Operation(summary = "List the events from the database.")
    public Response getAll() {
        String events;
        try {
            events = eventRepository.getAll();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("[\"Unable to list events!\"]")
                    .build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(events)
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully listed the event."),
            @APIResponse(
                    responseCode = "500",
                    description = "Failed to list the event.")})
    @Operation(summary = "List the event by id from the database.")
    public Response getById(@Parameter(
                                   description = "Object id of the event to find.",
                                   required = true
                           )
                           @PathParam("id") String id) {

        String events;
        try {
            events = eventRepository.getById(id);
        }
        catch (IllegalArgumentException e){
            e.printStackTrace(System.out);
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("[\"Incorrect ID!\"]")
                    .build();
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("[\"Unable to find event!\"]")
                    .build();
        }

        if(events==null){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        return Response
                .status(Response.Status.OK)
                .entity(events)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully updated event."),
            @APIResponse(
                    responseCode = "400",
                    description = "Invalid object id or event configuration."),
            @APIResponse(
                    responseCode = "404",
                    description = "Event object id was not found.") })
    @Operation(summary = "Update the event in the database.")
    public Response update(Event event,
                           @Parameter(
                                   description = "Object id of the event to update.",
                                   required = true
                           )
                           @PathParam("id") String id) {

        JsonArray violations = validator.getViolations(event);

        if (!violations.isEmpty()) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(violations.toString())
                    .build();
        }

        String events;
        try {
            events = eventRepository.updateById(id,event);
        }
        catch (IllegalArgumentException e){
            e.printStackTrace(System.out);
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("[\"Incorrect ID!\"]")
                    .build();
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("[\"Unable to find event!\"]")
                    .build();
        }

        if(events==null){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        return Response
                .status(Response.Status.OK)
                .entity(events)
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully deleted event."),
            @APIResponse(
                    responseCode = "400",
                    description = "Invalid object id."),
            @APIResponse(
                    responseCode = "404",
                    description = "Event object id was not found.") })
    @Operation(summary = "Delete the event from the database.")
    public Response remove(
            @Parameter(
                    description = "Object id of the event to delete.",
                    required = true
            )
            @PathParam("id") String id) {

        String events;
        try {
            events = eventRepository.deleteById(id);
        }
        catch (IllegalArgumentException e){
            e.printStackTrace(System.out);
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("[\"Incorrect ID!\"]")
                    .build();
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("[\"Unable to find event!\"]")
                    .build();
        }

        if(events==null){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        return Response
                .status(Response.Status.OK)
                .entity(events)
                .build();
    }
}

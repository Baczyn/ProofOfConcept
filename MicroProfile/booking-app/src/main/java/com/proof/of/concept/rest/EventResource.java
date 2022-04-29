package com.proof.of.concept.rest;

import com.proof.of.concept.model.Event;
import com.proof.of.concept.model.EventRequest;
import com.proof.of.concept.repository.EventRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.util.List;

@RequestScoped
@Path("/event")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventResource {

    @Inject
    EventRepository eventRepository;

    @GET
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully listed the events."),
            @APIResponse(
                    responseCode = "500",
                    description = "Failed to list the events.")})
    @Operation(summary = "List the events from the database.")
    public Response getAll() {
        List<Event> eventList;
        try{
            eventList = eventRepository.getAll();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(eventList)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @POST
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully added event."),
            @APIResponse(
                    responseCode = "400",
                    description = "Invalid event configuration.")})
    @Operation(summary = "Add a new event to the database.")
    public Response create(EventRequest eventRequest) {
        Event newEvent = eventRepository.create(new Event(eventRequest));
        return Response
                .status(Response.Status.OK)
                .entity(newEvent)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @DELETE
    @Path("{id}")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully deleted event."),
            @APIResponse(
                    responseCode = "400",
                    description = "Event object id was not found.") })
    @Operation(summary = "Delete the event from the database.")
    public Response delete(@PathParam("id") Integer eventId){
        Event event = eventRepository.delete(eventId);
        return Response
                .status(event==null?Response.Status.NOT_FOUND:Response.Status.OK)
                .entity(event)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @PUT
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully updated event."),
            @APIResponse(
                    responseCode = "400",
                    description = "Event object id was not found.") })
    @Operation(summary = "Update the event in the database.")
    public Response update(EventRequest eventRequest){
        Event updatedEvent = eventRepository.update(new Event(eventRequest));
        return Response
                .status(updatedEvent==null?Response.Status.NOT_FOUND:Response.Status.OK)
                .entity(updatedEvent)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}

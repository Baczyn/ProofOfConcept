package com.proof.of.concept.rest;

import com.proof.of.concept.model.CommentEventRequest;
import com.proof.of.concept.model.EventComments;
import com.proof.of.concept.repository.NeoRepository;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.util.List;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReviewResource {

    @Inject
    NeoRepository neoRepository;

    @POST
    @Path("like/{eventId}/{userName}")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully liked the event."),
            @APIResponse(
                    responseCode = "500",
                    description = "Failed to like the event.")})
    @Operation(summary = "Like the event.")
    @RolesAllowed({"ADMIN", "USER"})
    public Response likeEvent(@PathParam("eventId") String eventId, @PathParam("userName") String userName) {
        try {
            neoRepository.likeEvent(userName, eventId);
            return Response
                    .status(Response.Status.OK)
                    .build();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("[\"Unable to like the event!\"]")
                    .build();
        }
    }

    @DELETE
    @Path("like/{eventId}/{userName}")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully unliked the event."),
            @APIResponse(
                    responseCode = "500",
                    description = "Failed to unlike the event.")})
    @Operation(summary = "Dislike the event.")
    @RolesAllowed({"ADMIN", "USER"})
    public Response dislike(@PathParam("eventId") String eventId, @PathParam("userName") String userName) {
        try {
            neoRepository.dislikeEvent(userName, eventId);
            return Response
                    .status(Response.Status.OK)
                    .build();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("[\"Unable to unlike the event!\"]")
                    .build();
        }
    }

    @POST
    @Path("comment/{eventId}/{userName}")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully commented the event."),
            @APIResponse(
                    responseCode = "500",
                    description = "Failed to comment the event.")})
    @Operation(summary = "Comment the event.")
    @RolesAllowed({"ADMIN", "USER"})
    public Response commentEvent(@PathParam("eventId") String eventId, @PathParam("userName") String userName, CommentEventRequest request) {
        try {
            neoRepository.commentEvent(userName, eventId, request.getContent());
            return Response
                    .status(Response.Status.OK)
                    .build();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("[\"Unable to comment the event!\"]")
                    .build();
        }
    }

    @DELETE
    @Path("comment/{commentId}")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully uncommented the event."),
            @APIResponse(
                    responseCode = "500",
                    description = "Failed to uncomment the event.")})
    @Operation(summary = "Uncomment the event.")
    @RolesAllowed({"ADMIN", "USER"})
    public Response uncommentEvent(@PathParam("commentId") Long commentId) {
        try {
            neoRepository.uncommentEvent(commentId);
            return Response
                    .status(Response.Status.OK)
                    .build();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("[\"Unable to uncomment the event!\"]")
                    .build();
        }
    }

    @GET
    @Path("like/{eventId}")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully got all users who liked the event."),
            @APIResponse(
                    responseCode = "500",
                    description = "Failed to get all users who liked the event.")})
    @Operation(summary = "Get all users who liked the event.")
    @PermitAll
    public Response getAllLikesForEvent(@PathParam("eventId") String eventId) {
        try {
            List<String> likesForEvent = neoRepository.getAllLikesForEvent(eventId);
            return Response
                    .status(Response.Status.OK)
                    .entity(likesForEvent)
                    .build();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("[\"Unable to get all users who liked the event!\"]")
                    .build();
        }
    }

    @GET
    @Path("comment/{eventId}")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully got all comments for the event."),
            @APIResponse(
                    responseCode = "500",
                    description = "Failed to get all comments for the event.")})
    @Operation(summary = "Get all comments for the event.")
    @PermitAll
    public Response getAllCommentsForEvent(@PathParam("eventId") String eventId) {
        try {
            List<EventComments> eventComments = neoRepository.getAllCommentsForEvent(eventId);
            return Response
                    .status(Response.Status.OK)
                    .entity(eventComments)
                    .build();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("[\"Unable to get all comments for the event!\"]")
                    .build();
        }
    }

    @GET
    @Path("islike/{eventId}/{userName}")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully checked is event liked by user."),
            @APIResponse(
                    responseCode = "500",
                    description = "Failed to check is event liked by user.")})
    @Operation(summary = "Check if event is liked by user.")
    @RolesAllowed({"ADMIN", "USER"})
    public Response isEventLikedByUser(@PathParam("eventId") String eventId, @PathParam("userName") String userName) {
        try {
            boolean isLiked = neoRepository.isEventLikedByUser(userName, eventId);
            return Response
                    .status(Response.Status.OK)
                    .entity(isLiked)
                    .build();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("[\"Unable to check is event liked by user.!\"]")
                    .build();
        }
    }
}

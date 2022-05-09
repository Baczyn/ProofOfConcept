package com.proof.of.concept.frontend.client;

import com.proof.of.concept.frontend.model.review.CommentEventRequest;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://localhost:9083")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("review-app")
public interface ReviewClient extends AutoCloseable {

    @POST
    @Path("like/{eventId}/{userName}")
    Response likeEvent(@HeaderParam("Authorization") String authHeader, @PathParam("eventId") String eventId, @PathParam("userName") String userName);

    @DELETE
    @Path("like/{eventId}/{userName}")
    Response dislike(@HeaderParam("Authorization") String authHeader, @PathParam("eventId") String eventId, @PathParam("userName") String userName);

    @POST
    @Path("comment/{eventId}/{userName}")
    Response commentEvent(@HeaderParam("Authorization") String authHeader, @PathParam("eventId") String eventId, @PathParam("userName") String userName, CommentEventRequest request);

    @DELETE
    @Path("comment/{commentId}")
    Response uncommentEvent(@HeaderParam("Authorization") String authHeader, @PathParam("commentId") Long commentId);

    @GET
    @Path("like/{eventId}")
    Response getAllLikesForEvent(@PathParam("eventId") String eventId);

    @GET
    @Path("comment/{eventId}")
    Response getAllCommentsForEvent(@PathParam("eventId") String eventId);

    @GET
    @Path("islike/{eventId}/{userName}")
    Response isEventLikedByUser(@HeaderParam("Authorization") String authHeader, @PathParam("eventId") String eventId, @PathParam("userName") String userName);
}

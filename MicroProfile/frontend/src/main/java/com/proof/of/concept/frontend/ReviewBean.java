package com.proof.of.concept.frontend;

import com.proof.of.concept.frontend.client.ReviewClient;
import com.proof.of.concept.frontend.model.review.CommentEventRequest;
import com.proof.of.concept.frontend.model.review.CommentEventResponse;
import com.proof.of.concept.frontend.util.SessionUtils;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.json.JsonArray;
import jakarta.json.JsonValue;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.core.Response;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ConversationScoped
@Named
@Data
public class ReviewBean implements Serializable {

    @Serial
    private static final long serialVersionUID = 4771270804699990999L;

    @Inject
    private Conversation conversation;

    @Inject
    ReviewClient reviewClient;

    private String content;
    private String eventId;
    private boolean isLiked;

    @PostConstruct
    private void setIdFromParam(){
        this.eventId = SessionUtils.getEventIdFormParam();
    }

    public void initConversation(){
        if (!FacesContext.getCurrentInstance().isPostback()
                && conversation.isTransient()) {
            conversation.begin();
            System.out.println("initConversation");
        }
    }

    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
        System.out.println("endConversation");
    }

    public List<CommentEventResponse> getAllCommentsForEvent() {
        List<CommentEventResponse> list = new ArrayList<>();

        try {
            Response response = reviewClient.getAllCommentsForEvent(this.eventId);
            JsonArray array = response.readEntity(JsonArray.class);

            for (JsonValue item : array) {
                CommentEventResponse response2 = JsonbBuilder.create().fromJson(item.toString(), CommentEventResponse.class);
                list.add(response2);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return list;
    }

    public String commentEvent() {
        String jwt = SessionUtils.getJwtToken();
        String userName = SessionUtils.getCurrentUserName();

        try {
            reviewClient.commentEvent(jwt, this.eventId, userName, new CommentEventRequest(content));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "eventDetails.jsf?faces-redirect=true&id=" + this.eventId;
    }

    public String uncommentEvent(String commentId) {
        String jwt = SessionUtils.getJwtToken();

        try {
            reviewClient.uncommentEvent(jwt,Long.valueOf(commentId));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "eventDetails.jsf?faces-redirect=true&id=" + this.eventId;
    }

    public Integer getAllLikesForEventCount(){
        String userName = SessionUtils.getCurrentUserName();
        try {
               List<String> users = reviewClient.getAllLikesForEvent(this.eventId).readEntity(List.class);
               this.isLiked =  users.stream().anyMatch(user->user.equals(userName));
               return Optional.of(users).orElse(null).size();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public String likeEvent(){
        String jwt = SessionUtils.getJwtToken();
        String userName = SessionUtils.getCurrentUserName();
        try {
            reviewClient.likeEvent(jwt,this.eventId,userName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "eventDetails.jsf?faces-redirect=true&id=" + this.eventId;
    }

    public String unlikeEvent(){
        String jwt = SessionUtils.getJwtToken();
        String userName = SessionUtils.getCurrentUserName();
        try {
            reviewClient.dislike(jwt,eventId,userName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "eventDetails.jsf?faces-redirect=true&id=" + this.eventId;
    }
}

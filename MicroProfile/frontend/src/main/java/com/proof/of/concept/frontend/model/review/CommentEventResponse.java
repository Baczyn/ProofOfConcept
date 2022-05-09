package com.proof.of.concept.frontend.model.review;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentEventResponse {

    private String commentId;
    private String date;
    private String content;
    private String username;
}

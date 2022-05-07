package com.proof.of.concept.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentEventRequest {

    private String content;
}

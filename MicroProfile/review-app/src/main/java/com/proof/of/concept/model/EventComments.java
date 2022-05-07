package com.proof.of.concept.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventComments {

    private String commentId;
    private String date;
    private String content;
    private String username;

}

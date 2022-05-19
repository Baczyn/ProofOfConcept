package com.proof.of.concept.frontend.model.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {

    private Integer id;
    private String taskState;
    private String username;
    private String eventId;
    private String title;

}

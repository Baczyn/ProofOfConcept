package com.proof.of.concept.model;

import com.proof.of.concept.async.Task;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskResponse {

    private Integer id;
    private String taskState;
    private String username;
    private String eventId;

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.taskState = task.getTaskState().name();
        this.username = task.getUsername();
        this.eventId = task.getEventId();
    }
}

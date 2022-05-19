package com.proof.of.concept.async;

import com.proof.of.concept.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Task {

    private Integer id;
    private TaskState taskState;
    private String username;
    private String eventId;

    public Task(Order order, TaskState taskState) {
        this.id = order.getId();
        this.taskState = taskState;
        this.username = order.getUsername();
        this.eventId = order.getEvent().getEventId();
    }

    public void updateState(TaskState taskState) {
        this.taskState = taskState;
    }
}

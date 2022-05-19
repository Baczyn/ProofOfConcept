package com.proof.of.concept.async;

import com.proof.of.concept.model.Order;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@ApplicationScoped
public class AsyncTasksStateHolder {

    Queue<Task> taskQueue = new ConcurrentLinkedQueue<>();

    public Task initTask(Order order) {
        Task task = new Task(order, TaskState.NEW);
        taskQueue.add(task);
        return task;
    }

    public List<Task> getTasksByUsername(String username) {
        return taskQueue
                .stream()
                .filter(task -> task.getUsername().equals(username))
                .filter(task -> task.getTaskState().equals(TaskState.IN_PROGRESS) || task.getTaskState().equals(TaskState.FAILED))
                .collect(Collectors.toList());
    }

    public List<Task> getTasks() {
        return taskQueue.stream().toList();
    }
}

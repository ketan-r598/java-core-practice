package io.java_core.taskschedulerwithratelimiting.model;

import java.util.Objects;
import java.util.concurrent.Callable;

public class Task<T> {
    private final int taskId;
    private final int userId;
    private final Callable<T> payload;
    private volatile TaskStatus status;

    public Task(int taskId, int userId, TaskStatus status, Callable<T> payload) {
        this.taskId = taskId;
        this.userId = userId;
        this.payload = payload;
        this.status = status;
    }

    //    Getters
    public int getTaskId() {
        return taskId;
    }

    public int getUserId() {
        return userId;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Callable<T> getPayload() {
        return payload;
    }


    //    Setters
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Task)) return false;
        Task<?> task = (Task<?>) o;
        return taskId == task.taskId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(taskId);
    }

    @Override
    public String toString() {
        return "Task{" +
                "userId=" + userId +
                ", taskId=" + taskId +
                ", status=" + status +
                '}';
    }
}

package io.java_core.taskschedulerwithratelimiting.service;

import io.java_core.taskschedulerwithratelimiting.model.Task;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TaskScheduler {

    private final BlockingQueue<Task> taskQueue;

    public TaskScheduler() {
        taskQueue = new LinkedBlockingQueue<>();
    }

    public boolean addTask(Task task) {
        return taskQueue.offer(task);
    }

    public Optional<Task> getTask() throws InterruptedException {
        return Optional.ofNullable(taskQueue.take());
    }
}

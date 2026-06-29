package io.java_core.taskschedulerwithratelimiting.service;

import io.java_core.taskschedulerwithratelimiting.model.Task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TaskScheduler {

    private final BlockingQueue<Task> taskQueue;

    public TaskScheduler() {
        taskQueue = new LinkedBlockingQueue<>(5);
    }

    public boolean addTask(Task task) {
        try {
            return taskQueue.offer(task,10, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public Task getTask() throws InterruptedException {
        return taskQueue.take();
    }
}

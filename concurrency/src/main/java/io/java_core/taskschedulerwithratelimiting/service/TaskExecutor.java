package io.java_core.taskschedulerwithratelimiting.service;

import io.java_core.taskschedulerwithratelimiting.model.Task;
import io.java_core.taskschedulerwithratelimiting.model.TaskStatus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TaskExecutor {
    private final ExecutorService executorService;
    private TaskScheduler taskScheduler;
    private volatile boolean running = true;
    private Thread consumerThread;
    private RateLimiter rateLimiter;

    public TaskExecutor(TaskScheduler taskScheduler, RateLimiter rateLimiter) {
        executorService = Executors.newFixedThreadPool(5);
        this.taskScheduler = taskScheduler;
        this.rateLimiter = rateLimiter;
    }

    public void start() {
        this.consumerThread = new Thread(() -> {
            while (running) {
                try {
                    Task task = taskScheduler.getTask();

                    // Put the guards here meaning use the limiter here...
                    if (rateLimiter.tryAcquire(task.getUserId())) {
                        execute(task);
                    } else {
                        System.err.println("Request Limit Reached... Try after sometime...Thread - [ " + Thread.currentThread().getName() + " ] and Task - [ " + task.toString() + " ]");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        consumerThread.start();
    }

    private <T> void execute(Task<T> task) {
        task.setStatus(TaskStatus.RUNNING);

        executorService.submit(() -> {
            try {
                var result = task.getPayload().call();
                task.setStatus(TaskStatus.COMPLETED);
                System.out.println(result);
            } catch (Exception e) {
                task.setStatus(TaskStatus.FAILED);
                System.out.println("Task - " + task + " failed on Thread - [ " + Thread.currentThread().getName() + " ]");
                System.err.println("Exception " + e.getMessage());
            }
        });
    }

    public void shutdown() {

        running = false;
        consumerThread.interrupt();

        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();

                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Pool did not shutdown completely...");
                }
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

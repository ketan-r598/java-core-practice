package io.java_core.taskschedulerwithratelimiting.service;

import io.java_core.taskschedulerwithratelimiting.model.Task;
import io.java_core.taskschedulerwithratelimiting.model.TaskStatus;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TaskExecutor {
    private final ExecutorService executorService;
    private TaskScheduler taskScheduler;
    private volatile boolean running = true;
    private Thread consumerThread;

    public TaskExecutor(TaskScheduler taskScheduler) {
        executorService = Executors.newFixedThreadPool(5);
        this.taskScheduler = taskScheduler;
    }

    public void start() {
        this.consumerThread = new Thread(() -> {
            while (running) {
                try {
                    Optional<Task> t = taskScheduler.getTask();

                    if (t.isPresent()) {
                        execute(t.get());
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
                T result = task.getPayload().call();
                task.setStatus(TaskStatus.COMPLETED);
                System.out.println(result);
                return result;
            } catch (Exception e) {
                task.setStatus(TaskStatus.FAILED);
                throw e;
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

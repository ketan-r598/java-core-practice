package io.java_core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolDemo {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(4);

        List<Future<Integer>> futureList = new ArrayList<>();

        for(int i = 0; i < 20; ++i) {
            final int taskId = i;
            Callable<Integer> task = () -> {
                System.out.println("Task - " + taskId + "started on " + Thread.currentThread().getName());
                Thread.sleep(2000);
                System.out.println("Task - " + taskId + " completed.");
                return taskId;
            };

            futureList.add(executor.submit(task));
        }

        futureList.forEach((item) -> {
            try {
                System.out.println(item.get());
            } catch (InterruptedException | ExecutionException e) {
                System.out.println(e.getMessage());
            }
        });

        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();  // Force kill
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

package io.java_core.basicconcurrencypatterns.semaphores;

//Purpose
//Ensure Thread A completes an action before Thread B starts a dependent action.


import java.util.concurrent.Semaphore;

public class SingnallingDemo {

    private final Semaphore taskADone;

    public SingnallingDemo() {
        taskADone = new Semaphore(0);
    }

    public void taskA() {
        String name = Thread.currentThread().getName();
        System.out.println("Entered in Thread - [ " + name + " ]");
        System.out.println("Task A is done in Thread - [ "+ name + " ]");
        taskADone.release();
    };

    public void taskB() {
        String name = Thread.currentThread().getName();
        System.out.println("Entered in Thread - [ " + name + " ]");
        System.out.println("Waiting for task A to complete");

        try {
            taskADone.acquire();
            System.out.println("Task A is Done. Starting task B...");
            System.out.println("Task B is done in Thread - [ " + name + " ]");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            return;
        }

    };
}

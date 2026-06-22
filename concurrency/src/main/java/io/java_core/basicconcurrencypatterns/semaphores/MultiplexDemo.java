package io.java_core.basicconcurrencypatterns.semaphores;

import java.util.concurrent.Semaphore;

//Purpose
//Allow up to N threads in the critical section simultaneously.


public class MultiplexDemo {

    private int count;
    private final Semaphore multiplex;

    public MultiplexDemo() {
        count = 3;
        multiplex = new Semaphore(count);
    }

    public void task() {
        String name = Thread.currentThread().getName();

        System.out.println("Thread - [ " + name + " ] started...");

        try {
            multiplex.acquire();
            System.out.println("Thread - [ " + name + " ] is performing the task...");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            multiplex.release();
            System.out.println("Thread - [ " + name + " ] completed the task...");
        }
    }
}

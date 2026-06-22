package io.java_core.basicconcurrencypatterns.javaconcurrencyutilities;

import java.util.concurrent.CountDownLatch;

public class RendezvousDemo {

    private final CountDownLatch taskADone;
    private final CountDownLatch taskBDone;

    public RendezvousDemo() {
        taskADone = new CountDownLatch(1);
        taskBDone = new CountDownLatch(1);
    }

    public void taskA() {
        String name = Thread.currentThread().getName();
        System.out.println("Statement-1 from Thread - [ " + name + " ]");
        taskADone.countDown();
        try {
            taskBDone.await();
            System.out.println("Statement-2 from Thread - [ " + name + " ]");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void taskB() {
        String name = Thread.currentThread().getName();
        System.out.println("Statement-1 from Thread - [ " + name + " ]");
        taskBDone.countDown();
        try {
            taskADone.await();
            System.out.println("Statement-2 from Thread - [ " + name + " ]");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

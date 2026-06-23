package io.java_core.basicconcurrencypatterns.javaconcurrencyutilities;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SignallingDemo {

    private final CountDownLatch a1Done;

    public SignallingDemo() {
        a1Done = new CountDownLatch(1);
    }

    public void taskA() {
        String name  = Thread.currentThread().getName();

        System.out.println("Thread [ " + name + " ] completed its task...");
        a1Done.countDown();
    }
    public void taskB () {
        String name = Thread.currentThread().getName();
        try {
            if(a1Done.await(500, TimeUnit.MILLISECONDS)) {
                System.out.println("Thread [ " + name + " ] completed its task...");
            } else {
                System.out.println("Could not acquire the lock...");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

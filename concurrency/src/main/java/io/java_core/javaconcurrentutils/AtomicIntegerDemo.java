package io.java_core.javaconcurrentutils;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {

    private AtomicInteger count = new AtomicInteger();

    public void increment() {
        count.incrementAndGet();
    }

    public int getCounter() {
        return count.get();
    }


    public static void main(String[] args) {
        AtomicIntegerDemo atomicIntegerDemo = new AtomicIntegerDemo();

        Runnable task = () -> {
            for(int i = 0; i < 100_000; ++i) atomicIntegerDemo.increment();
        };

        Thread worker_1 = new Thread(task);

        Thread worker_2 = new Thread(task);

        worker_1.start();
        worker_2.start();

        try {
            worker_1.join();
            worker_2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Expected value is 200_000");
        System.out.println("Actual value: " + atomicIntegerDemo.getCounter());
    }
}

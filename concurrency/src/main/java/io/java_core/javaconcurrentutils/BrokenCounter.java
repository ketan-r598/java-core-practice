package io.java_core.javaconcurrentutils;

public class BrokenCounter {

    private int count = 0;

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }


    public static void main(String[] args) {
        BrokenCounter brokenCounter = new BrokenCounter();

        Thread worker_1 = new Thread(() -> {
            for(int i = 0; i < 100_000; ++i) brokenCounter.increment();
        });

        Thread worker_2 = new Thread(() -> {
            for(int i = 0; i < 100_000; ++i) brokenCounter.increment();
        });

        worker_1.start();
        worker_2.start();

        try {
            worker_1.join();
            worker_2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Expected value is 200_000");
        System.out.println("Actual value: " + brokenCounter.getCount());
    }
}


class FixedCounter {

    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }


    public static void main(String[] args) {
        FixedCounter fixedCounter = new FixedCounter();

        Thread worker1 = new Thread(() -> {
            for(int i = 0; i < 100_000; ++i) fixedCounter.increment();
        });

        Thread worker2 = new Thread(() -> {
            for(int i = 0; i < 100_000; ++i) fixedCounter.increment();
        });

        worker1.start();
        worker2.start();

        try {
            worker1.join();
            worker2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Expected value is 200_000");
        System.out.println("Actual value: " + fixedCounter.getCount());
    }
}

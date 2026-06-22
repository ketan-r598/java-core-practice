package io.java_core;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

    private int count = 0;
    private final ReentrantLock reentrantLock = new ReentrantLock();

    public void increment() {
        reentrantLock.lock();
        try {
            count++;
        } finally {
            reentrantLock.unlock();
        }
    }

    public int getCount() {
        reentrantLock.lock();

        try {
            return count;
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLockDemo lockDemo = new ReentrantLockDemo();

        Runnable task = () -> {for(int i = 0; i < 100_000; ++i) lockDemo.increment();};

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

        System.out.println("Expected value: 200_000");
        System.out.println("Actual value: " + lockDemo.getCount());
    }

}

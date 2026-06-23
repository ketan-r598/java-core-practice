package io.java_core.basicconcurrencypatterns.semaphores;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

//Purpose
//Ensure only one thread at a time accesses a shared resource (critical section).


public class MutexDemo {

    private final Semaphore mutex;
    private int count;

    public MutexDemo() {
        mutex = new Semaphore(1);
        count = 101;
    }

    public void increment() {
        for(int i = 0; i < 500_000; ++i) {
            try {
                if(mutex.tryAcquire(500, TimeUnit.MILLISECONDS)) {
                    count++;
                    mutex.release();
                } else {
                    System.out.println("Not able to acquire the lock...");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void decrement() {
        for(int i = 0; i < 500_000; ++i) {
            try {
                if(mutex.tryAcquire(500, TimeUnit.MILLISECONDS)) {
                    count--;
                    mutex.release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void displayCount() {
        System.out.println("Value of count: " + count);
    }
}
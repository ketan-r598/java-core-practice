package io.java_core.basicconcurrencypatterns.javaconcurrencyutilities;

// Can be done using ReentrantLock:: or semaphores

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class MutexDemo {

    private int count;
    private final ReentrantLock lock;

    public MutexDemo() {
        count = 101;
        lock = new ReentrantLock();
    }

    public void increment() {
        for(int i = 0; i < 100_000; ++i) {
            try {
                if(lock.tryLock(500, TimeUnit.MILLISECONDS)) {
                    count++;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }

    public void decrement() {
        for(int i = 0; i < 100_000; ++i) {
            try {
                if(lock.tryLock(500, TimeUnit.MILLISECONDS)) {
                    count--;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }

    public void display() {
        System.out.println("Count - " + count);
    }
}
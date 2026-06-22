package io.java_core.basicconcurrencypatterns.javaconcurrencyutilities;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class BarrierDemo {

    private final CountDownLatch barrier;
    private final int N_THREADS;

    public BarrierDemo(int n) {
        N_THREADS = n;
        barrier = new CountDownLatch(N_THREADS);
    }

    public void task() {
        String name = Thread.currentThread().getName();
        System.out.println("Thread - [ " + name + " ] has started...");
        try {
            Thread.sleep(500);
            System.out.println("Thread - [ " + name + " ] has completed phase-1...");
            barrier.countDown();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        try {
            barrier.await();
            System.out.println("Thread - [ " + name + " ] has started phase-2...");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

package io.java_core.basicconcurrencypatterns.javaconcurrencyutilities;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ReusableBarrierDemo {

    private final int N_THREADS;
    private final CyclicBarrier barrier;

    public ReusableBarrierDemo(int n) {
        N_THREADS = n;
        barrier = new CyclicBarrier(N_THREADS, () -> System.out.println("Barrier is reached... Barrier is reset"));
    }

    public void task() {
        String name = Thread.currentThread().getName();

        System.out.println("Thread - [ " + name + " ] has started...");

        try {
            for(int i = 0; i < 5; ++i) {
                Thread.sleep(500);
                System.out.println("Thread - [ " + name + " ] has completed " + i +" th iteration...");
                barrier.await();
            }
            System.out.println("Thread - [ " + name + " ] completed the task");
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
        }
    }
}

package io.java_core.basicconcurrencypatterns.semaphores;

import java.util.concurrent.Semaphore;

// Purpose
// All N threads must arrive before any can proceed. Generalizes Rendezvous to N threads.
public class BarrierDemo {

    private int count;
    private final Semaphore mutex;
    private final Semaphore barrier;
    private final int N;
    public BarrierDemo() {
        count = 0;
        N = 3;
        mutex = new Semaphore(1);
        barrier = new Semaphore(0);
    }

    public void task() {

        String name = Thread.currentThread().getName();
//        Phase 1 task -

        System.out.println("Thread [ " + name + " ] started the task...");

        try {
            Thread.sleep(500);
            System.out.println("Thread [ " + name + " ] completed the phase-1 task...");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // barrier
        try {
            mutex.acquire();
            count++;
            if(count == N) { barrier.release();}
        } catch (InterruptedException e) {
            Thread.currentThread().getName();
        } finally {
            mutex.release();
        }

        try {
            barrier.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            barrier.release();
        }

//        Next Phase starts
        System.out.println("Thread [ " + name + " ] started Phase-2...");
    }
}

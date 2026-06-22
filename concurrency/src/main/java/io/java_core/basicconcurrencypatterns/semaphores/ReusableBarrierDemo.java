package io.java_core.basicconcurrencypatterns.semaphores;

import java.util.concurrent.Semaphore;

//Purpose
//Threads run in a loop and need to barrier repeatedly. The barrier must auto-reset after all pass through.

public class ReusableBarrierDemo {

    private int count = 0;
    private final int N_THREADS;
    private final Semaphore mutex;
    private final Semaphore turnstile1;
    private final Semaphore turnstile2;

    public ReusableBarrierDemo(int n) {
        N_THREADS = n;
        mutex = new Semaphore(1);
        turnstile1 = new Semaphore(0);
        turnstile2 = new Semaphore(1);
    }

    public void task() {
        String name = Thread.currentThread().getName();
        for(int i = 0; i < 5; ++i) {

//            Phase-1...
            System.out.println("Thread - [ " + name + " ] has entered Phase-1...");

            try {

                System.out.println("Thread - [ " + name + " ] has started the Phase-1...");
                Thread.sleep(500);
                System.out.println("Thread - [ " + name + " ] has finished the Phase-1...");

                mutex.acquire();
                count++;

                if(count == N_THREADS) {
                    turnstile1.release();
                    turnstile2.acquire();
                }
                mutex.release();

                turnstile1.acquire();
                turnstile1.release();

//            Phase - 2
                System.out.println("Thread - [ " + name + " ] has started task phase-2");
                Thread.sleep(500);
                System.out.println("Thread - [ " + name + " ] has finished the phase-2...");

                mutex.acquire();
                count--;
                if(count == 0) {
                    turnstile2.release();
                    turnstile1.acquire();
                }
                mutex.release();

                turnstile2.acquire();
                turnstile2.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

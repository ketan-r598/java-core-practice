package io.java_core.basicconcurrencypatterns.semaphores;

// Purpose
//Purpose
//Match threads from two queues pairwise. A leader waits for a follower, and vice versa.

import java.util.concurrent.Semaphore;

public class QueueDemo {

    private final int N_THREADS;
    private final Semaphore leaderQueue;
    private final Semaphore followerQueue;

    private final Semaphore leaderReady;
    private final Semaphore followerReady;

    public QueueDemo(int n) {
        N_THREADS = n;
        leaderQueue = new Semaphore(0);
        followerQueue = new Semaphore(0);

        leaderReady = new Semaphore(0);
        followerReady = new Semaphore(0);
    }

    public void leader() {
        String name = Thread.currentThread().getName();
            try {
                leaderQueue.release();
                followerQueue.acquire();

                Thread.sleep(500);

                leaderReady.release();
                followerReady.acquire();
                System.out.println("Leader - [ " + name + " ] is paired...");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
    }

    public void follower() {
        String name = Thread.currentThread().getName();
            try {
                followerQueue.release();
                leaderQueue.acquire();

                Thread.sleep(500);

                followerReady.release();
                leaderReady.acquire();
                System.out.println("Follower - [ " + name + " ] is paired...");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
    }
}

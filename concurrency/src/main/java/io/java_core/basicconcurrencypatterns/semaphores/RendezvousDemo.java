package io.java_core.basicconcurrencypatterns.semaphores;

//Purpose
//Both threads must wait for each other before continuing. Thread A finishes a1 before B does b2,
// AND B finishes b1 before A does a2.

// Here, what we are trying to achieve is that both task A and task B completes it any statement-1
// in any order and once both the task completes its 1st statement then only statement 2 both task
// will be executed in any order.

import java.util.concurrent.Semaphore;

public class RendezvousDemo {

    private final Semaphore a1Done;
    private final Semaphore b1Done;

    public RendezvousDemo() {
        a1Done = new Semaphore(0);
        b1Done = new Semaphore(0);
    }

    public void taskA() {
        String name = Thread.currentThread().getName();

        System.out.println("Statement 1 from thread - [ " + name + " ]");
        a1Done.release();
        try {
            b1Done.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Statement 2 from thread - [ " + name + " ]");
    }

    public void taskB() {
        String name = Thread.currentThread().getName();

        System.out.println("Statement 1 from thread - [ " + name + " ]");
        b1Done.release();
        try {
            a1Done.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Statement 2 from thread - [ " + name + " ]");
    }
}
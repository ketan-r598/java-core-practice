package io.java_core.javaconcurrentutils;

public class MyThread extends Thread {

    public void run() {
        System.out.println(Thread.currentThread().getName() + " running.");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

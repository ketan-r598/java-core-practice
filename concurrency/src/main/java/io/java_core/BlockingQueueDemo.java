package io.java_core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueDemo {

    private BlockingQueue<String> queue = new LinkedBlockingQueue<>(10);

    public void start() {

        Thread producer = new Thread(() -> {
            for(int i = 0; i < 20; ++i) {
                String order = "Order " + i;
                try {
                    queue.put(order);
                    Thread.sleep(100);
                    System.out.println("Created the " + order);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            try {
                queue.put("POISON");
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();;
            }
            System.out.println("Producer done!");
        });

        Thread consumer1 = new Thread(() -> {
            while(true) {
                try {
                    String order = queue.take();

                    if(order.equalsIgnoreCase("poison")) {
                        System.out.println("Poison encountered!!");
                        throw new InterruptedException();}

                    Thread.sleep(100);
                    System.out.println("Processed order consumer 1 " + order);
                } catch (InterruptedException e) {
                    System.out.println("consumer 1 is interrrupted!!");
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        Thread consumer2 = new Thread(() -> {
            while(true) {
                try {
                    String order = queue.take();
                    if(order.equalsIgnoreCase("poison")) {
                        System.out.println("Poison encountered!!");
                        throw new InterruptedException();}

                    Thread.sleep(100);
                    System.out.println("Processed order consumer 2 " + order);
                } catch (InterruptedException e) {
                    System.out.println("consumer 2 is interrrupted!!");
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        producer.start();
        consumer1.start();
        consumer2.start();


        try {
            producer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        while(!queue.isEmpty()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        consumer1.interrupt();
        consumer2.interrupt();
        System.out.println("All orders consumed");
    }

    public static void main(String[] args) {
        new BlockingQueueDemo().start();
    }
}

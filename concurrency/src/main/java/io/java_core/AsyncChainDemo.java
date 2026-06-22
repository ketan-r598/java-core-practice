package io.java_core;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class AsyncChainDemo {

    public static CompletableFuture<String> fetchUserId(String email) {

        Supplier<String> s = () -> {
            System.out.println("Fetching User ID for: " + email +" on " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "user-123";
        };

        return CompletableFuture.supplyAsync(s);
    }

    public static CompletableFuture<Integer> fetchOrderCount(String userId) {

        Supplier<Integer> s = () -> {
            System.out.println("Fetching orders for: " + userId + " on " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return 5;
        };

        return CompletableFuture.supplyAsync(s);
    }

    public static void main(String[] args) {
        String email = "abc@gmail.com";
        fetchUserId(email).thenCompose(userId -> fetchOrderCount(userId))
                          .thenAccept(count -> System.out.println("Order count " + count))
                          .join();
    }
}

package io.java_core.javaconcurrentutils;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ParallelFetchDemo {

    public static CompletableFuture<String> fetchUserProfile(int userId) {


        Supplier<String> s = () -> {
        if(userId == -1) throw new RuntimeException("Invalid id");
            System.out.println("Fetching profile of " + userId);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            return "Profile of user " + userId;
        };

        return CompletableFuture.supplyAsync(s);
    }

    public static CompletableFuture<Integer> fetchUserOrders(int userId) {

        Supplier<Integer> s = () -> {
            System.out.println("Fetching order of User " + userId);
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
        int userId = -1;
        CompletableFuture<String> profile = fetchUserProfile(userId);
        CompletableFuture<Integer> orders = fetchUserOrders(userId);

        String result = profile
                .thenCombine(orders, (p,o) -> "User: " + userId + " | Profile: " + p + " | Orders: " + o)
                .exceptionally(ex -> "Failed: " + ex.getMessage())
                .join();

        System.out.println(result);
    }
}

package io.java_core.taskschedulerwithratelimiting.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RateLimiter {

    private ConcurrentHashMap<Integer, TokenBucket> tokenBucket;

    public RateLimiter() {
        tokenBucket = new ConcurrentHashMap<>();
    }

    public boolean tryAcquire(int userId) {

        // if not available in thw queue, create the userId with defaults
        if (!tokenBucket.contains(userId)) addToMap(userId);
        refill(userId);

        AtomicInteger token = tokenBucket.get(userId).getToken();
        int current;

        // Special CAS Loop - Magic behind lock free code... Great stuff to understand.
        do {
            current = token.get();
            if (current <= 0) return false;
        } while (!token.compareAndSet(current, current - 1));

        return true;
    }

    private void refill(int userId) {

        // Simple way to handle synchronization...
        TokenBucket tb = tokenBucket.get(userId);
        synchronized (tb) {
            long current = System.currentTimeMillis();
            long before = tb.getLastRefill().get();
            long secondsElapsed = (current-before)/1000;
            long numberOfTokensIncrease = secondsElapsed * tb.getINCREMENT_FAC();
            long newTokens = Long.min(tb.getToken().get() + numberOfTokensIncrease, tb.getMaxTokens());
            tb.getToken().set((int) newTokens);
            tb.getLastRefill().set(current);
        }
//        AtomicLong currentTime = new AtomicLong(System.currentTimeMillis());
//        AtomicLong lastRefill = tokenBucket.get(userId).getLastRefill();
//
//        if (currentTime.get() - lastRefill.get() > 1000) {
//            AtomicInteger newTokens = new AtomicInteger((int) Math.round(tokenBucket.get(userId).getToken().get() * tokenBucket.get(userId).getINCREMENT_FAC()));
//            tokenBucket.get(userId).setToken(newTokens);
//            tokenBucket.get(userId).setLastRefill(currentTime);
//        }
    }

    private void addToMap(int userId) {
        tokenBucket.putIfAbsent(userId, new TokenBucket());
    }


    private class TokenBucket {
        private AtomicInteger token;
        private final Integer maxTokens;
        private AtomicLong lastRefill;
        private final int INCREMENT_FAC = 2;

        TokenBucket() {
            maxTokens = 10;
            token = new AtomicInteger(maxTokens);
            lastRefill = new AtomicLong(System.currentTimeMillis());
        }

        public AtomicInteger getToken() {
            return token;
        }

        public int getINCREMENT_FAC() {
            return INCREMENT_FAC;
        }

        public AtomicLong getLastRefill() {
            return lastRefill;
        }

        public Integer getMaxTokens() {
            return maxTokens;
        }
    }
}

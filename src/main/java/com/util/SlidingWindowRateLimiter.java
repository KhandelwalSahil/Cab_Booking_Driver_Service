package com.util;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SlidingWindowRateLimiter implements RateLimiter {

    Queue<Long> slidingWindow;
    int bucketCapacity;
    int time;

    public SlidingWindowRateLimiter(int bucketCapacity, int time) {
        this.bucketCapacity = bucketCapacity;
        this.time = time;
        slidingWindow = new ConcurrentLinkedQueue<>();
    }

    @Override
    public boolean grantAccess() {
        long curTime = System.currentTimeMillis();
        updateQueue(curTime);
        if (slidingWindow.size() < bucketCapacity) {
            slidingWindow.offer(curTime);
            return true;
        }
        return false;
    }

    private void updateQueue(long curTime) {
        if (slidingWindow.isEmpty()) {
            return;
        }
        long time = (curTime - slidingWindow.peek()) / 1000;
        while (time >= this.time) {
            slidingWindow.poll();
            if (slidingWindow.isEmpty()) {
                return;
            }
            time = (curTime - slidingWindow.peek()) / 1000;
        }
    }
}

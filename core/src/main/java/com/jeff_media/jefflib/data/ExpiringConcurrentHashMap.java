package com.jeff_media.jefflib.data;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExpiringConcurrentHashMap<K,V> extends ConcurrentHashMap<K,V> {

    private final long expirationTime;
    private final long checkInterval;
    private final TimeUnit timeUnit;

    public ExpiringConcurrentHashMap(long expirationTime, long checkInterval, TimeUnit timeUnit) {
        this.expirationTime = expirationTime;
        this.checkInterval = checkInterval;
        this.timeUnit = timeUnit;

        new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(this::removeExpired, expirationTime, expirationTime, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    private void removeExpired() {

    }

}

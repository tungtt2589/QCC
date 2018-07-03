package com.storm.quora.cache;

import com.storm.quora.cache.redis.CacheConnection;
import com.storm.quora.cache.redis.CacheManager;

public class RedisCache {
    private static final Object lockThread = new Object();
    private static CacheManager cacheManager;

    public static CacheManager getManager() {
        synchronized (lockThread) {
            if (cacheManager == null) {
                System.out.println("Cache Connection is creating...");
                String CACHE_URI = "redis://:topica_123@171.244.3.242:6379";
                cacheManager = CacheConnection.create(CACHE_URI).getManager();
            }
            System.out.println("Cache Connection is created!");
            return cacheManager;
        }
    }

    public static void setCacheManager(CacheManager manager) {
        cacheManager = manager;
    }
}

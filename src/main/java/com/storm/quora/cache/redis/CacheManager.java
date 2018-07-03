package com.storm.quora.cache.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class CacheManager {
    private JedisPool underlyingPool = null;

    protected CacheManager(JedisPool pool) {
        this.underlyingPool = pool;
    }

    public GlobalCache getGlobalCache() {
        return new GlobalCache(this.underlyingPool);
    }

    public MainCache getMainCache() {
        return new MainCache(this.underlyingPool);
    }

    public Set<String> getAllKeyPattern(String keyPattern) {
        Jedis jedis = null;

        try {
            jedis = this.underlyingPool.getResource();
            Set<String> keys = jedis.keys(keyPattern);
            if (keys.size() > 0) {
                return keys;
            }
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }
        return null;
    }
}

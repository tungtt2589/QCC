package com.storm.quora.cache.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.net.URI;

public class CacheConnection {
    private JedisPool underlyingPool = null;

    public static CacheConnection createByHostOnly(String host) {
        JedisPoolConfig cfg = new JedisPoolConfig();
        cfg.setMaxTotal(30);
        cfg.setMaxIdle(20);
        cfg.setMinIdle(5);
        cfg.setMaxWaitMillis(30000L);
        JedisPool pool = new JedisPool(cfg, host);
        return new CacheConnection(pool);
    }

    public static CacheConnection create(String uri) {
        try {
            JedisPoolConfig cfg = new JedisPoolConfig();
            cfg.setMaxTotal(30);
            cfg.setMaxIdle(20);
            cfg.setMinIdle(5);
            cfg.setMaxWaitMillis(30000L);
            JedisPool pool = new JedisPool(cfg, new URI(uri));
            return new CacheConnection(pool);
        } catch (Exception var3) {
            return null;
        }
    }

    private CacheConnection(JedisPool pool) {
        this.underlyingPool = pool;
    }

    public CacheManager getManager() {
        return new CacheManager(this.underlyingPool);
    }
}

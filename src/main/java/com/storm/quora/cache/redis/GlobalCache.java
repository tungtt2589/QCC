package com.storm.quora.cache.redis;

import redis.clients.jedis.JedisPool;

public class GlobalCache extends CacheService {
    final String KEY_PREFIX = "global:";

    protected String getKeyPrefix() {
        return "global:";
    }

    protected GlobalCache(JedisPool pool) {
        super(pool);
    }
}

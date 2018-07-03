package com.storm.quora.cache.redis;

import redis.clients.jedis.JedisPool;

public class MainCache extends CacheService {
    @Override
    protected String getKeyPrefix() {
        return "";
    }

    protected MainCache(JedisPool pool) {
        super(pool);
    }
}

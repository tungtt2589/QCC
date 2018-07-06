package com.storm.quora.util;

import com.storm.quora.cache.RedisCache;
import com.storm.quora.cache.redis.CacheManager;
import com.storm.quora.cache.redis.MainCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisUtil {
    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    public static long getCountUpVote(String questionId) {
        String key = String.format(Constant.UP_VOTE_QUESTION_CACHE_FORMAT, questionId);
        try {
            CacheManager cm = RedisCache.getManager();
            if (cm != null) {
                MainCache mc = cm.getMainCache();
                return mc.hlen(key);
            }
        } catch (Exception ex) {
            logger.error("Can't get resource from pool!");
        }
        return 0;
    }

    public static long getCountDownVote(String questionId) {
        String key = String.format(Constant.DOWN_VOTE_QUESTION_CACHE_FORMAT, questionId);
        try {
            CacheManager cm = RedisCache.getManager();
            if (cm != null) {
                MainCache mc = cm.getMainCache();
                return mc.hlen(key);
            }
        } catch (Exception ex) {
            logger.error("Can't get resource from pool!");
        }
        return 0;
    }
}

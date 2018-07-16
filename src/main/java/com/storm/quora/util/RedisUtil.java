package com.storm.quora.util;

import com.storm.quora.cache.RedisCache;
import com.storm.quora.cache.redis.CacheManager;
import com.storm.quora.cache.redis.MainCache;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Set;

public class RedisUtil {
    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    private static long getCountUpVote(String questionId) {
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

    private static long getCountDownVote(String questionId) {
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

    public static JSONArray getAllInfoQuestion() {
        JSONArray array = new JSONArray();
        String keyPattern = "vote:question:*:up";
        try {
            CacheManager cm = RedisCache.getManager();
            if (cm != null) {
                MainCache mainCache = cm.getMainCache();
                Set<String> list = mainCache.lkeys(keyPattern);
                for (String key : list) {
                    String questionId = Arrays.asList(key.split(":")).get(2);
                    long upCount = getCountUpVote(questionId);
                    long downCount = getCountDownVote(questionId);
                    JSONObject object = new JSONObject();
                    object.put("id", questionId);
                    object.put("upCount", upCount);
                    object.put("downCount", downCount);

                    array.put(object);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return array;
    }
}

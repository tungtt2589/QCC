package com.storm.quora.controller;

import com.storm.quora.cache.RedisCache;
import com.storm.quora.cache.redis.CacheManager;
import com.storm.quora.cache.redis.MainCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VoteController {
    private static final Logger log = LoggerFactory.getLogger(VoteController.class);

    public ModelAndView upVote() {
        ModelAndView modelAndView = new ModelAndView();

        try {
            CacheManager cm = RedisCache.getManager();
            if (cm != null) {
                MainCache mc = cm.getMainCache();
                mc.setValue("test_cache", "test");
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return modelAndView;
    }

    public String downVote(Model model) {

        return "";
    }
}

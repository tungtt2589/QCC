package com.storm.quora.controller;

import com.storm.quora.cache.RedisCache;
import com.storm.quora.cache.redis.CacheManager;
import com.storm.quora.cache.redis.MainCache;
import com.storm.quora.config.UserAuthentication;
import com.storm.quora.dto.QuestionDTO;
import com.storm.quora.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class VoteController {
    private static final Logger log = LoggerFactory.getLogger(VoteController.class);

    private String qUpVote = "vote:question:%s:up";
    private String qDownVote = "vote:question:%s:down";
    private String aUpVote = "vote:answer:%s:up";
    private String aDownVote = "vote:answer:%s:down";

    @RequestMapping(value = "/upVoteQuestion")
    public ModelAndView upVoteQuestion(@ModelAttribute QuestionDTO questionDTO) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        String keyUp = String.format(qUpVote, questionDTO.getQuestionId());
        String keyDown = String.format(qDownVote, questionDTO.getQuestionId());
        modelAndView.setViewName("redirect:/");
        if (UserAuthentication.getCurrentUser() == null) {
            return modelAndView;
        }
        try {
            CacheManager cm = RedisCache.getManager();
            if (cm != null) {
                MainCache mc = cm.getMainCache();
                User currentUser = UserAuthentication.getCurrentUser();
                if (mc.exists(keyDown) && mc.hexists(keyDown, String.valueOf(currentUser.getUserId()))) {
                    mc.hdel(keyDown, String.valueOf(currentUser.getUserId()));
                }
                long timeNow = new Date().getTime();
                mc.hset(keyUp, String.valueOf(currentUser.getUserId()), String.valueOf(timeNow));
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/downVoteQuestion")
    public ModelAndView downVoteQuestion(@ModelAttribute QuestionDTO questionDTO) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        String keyUp = String.format(qUpVote, questionDTO.getQuestionId());
        String keyDown = String.format(qDownVote, questionDTO.getQuestionId());
        modelAndView.setViewName("index");
        if (UserAuthentication.getCurrentUser() == null) {
            return modelAndView;
        }
        try {
            CacheManager cm = RedisCache.getManager();
            if (cm != null) {
                MainCache mc = cm.getMainCache();
                User currentUser = UserAuthentication.getCurrentUser();
                if (mc.exists(keyUp) && mc.hexists(keyUp, String.valueOf(currentUser.getUserId()))) {
                    mc.hdel(keyUp, String.valueOf(currentUser.getUserId()));
                }
                long timeNow = new Date().getTime();
                mc.hset(keyDown, String.valueOf(currentUser.getUserId()), String.valueOf(timeNow));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/upVoteAnswer")
    public ModelAndView upVoteAnswer(@ModelAttribute QuestionDTO questionDTO) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        String keyUp = String.format(aUpVote, questionDTO.getQuestionId());
        String keyDown = String.format(aDownVote, questionDTO.getQuestionId());
        modelAndView.setViewName("index");
        if (UserAuthentication.getCurrentUser() == null) {
            return modelAndView;
        }
        try {
            CacheManager cm = RedisCache.getManager();
            if (cm != null) {
                MainCache mc = cm.getMainCache();
                User currentUser = UserAuthentication.getCurrentUser();
                if (mc.hexists(keyDown, String.valueOf(currentUser.getUserId()))) {
                    mc.hdel(keyDown, String.valueOf(currentUser.getUserId()));
                }
                long timeNow = new Date().getTime();
                mc.hset(keyUp, String.valueOf(currentUser.getUserId()), String.valueOf(timeNow));
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/downVoteAnswer")
    public ModelAndView downVoteAnswer(@ModelAttribute QuestionDTO questionDTO) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        String keyUp = String.format(aUpVote, questionDTO.getQuestionId());
        String keyDown = String.format(aDownVote, questionDTO.getQuestionId());
        modelAndView.setViewName("index");
        if (UserAuthentication.getCurrentUser() == null) {
            return modelAndView;
        }
        try {
            CacheManager cm = RedisCache.getManager();
            if (cm != null) {
                MainCache mc = cm.getMainCache();
                User currentUser = UserAuthentication.getCurrentUser();
                if (mc.hexists(keyUp, String.valueOf(currentUser.getUserId()))) {
                    mc.hdel(keyUp, String.valueOf(currentUser.getUserId()));
                }
                long timeNow = new Date().getTime();
                mc.hset(keyDown, String.valueOf(currentUser.getUserId()), String.valueOf(timeNow));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return modelAndView;
    }
}

package com.storm.quora.controller;

import com.storm.quora.cache.RedisCache;
import com.storm.quora.cache.redis.CacheManager;
import com.storm.quora.cache.redis.MainCache;
import com.storm.quora.common.AjaxResponseBody;
import com.storm.quora.config.UserAuthentication;
import com.storm.quora.dto.QuestionDTO;
import com.storm.quora.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class AnswerController {
    private static final Logger logger = LoggerFactory.getLogger(AnswerController.class);

    private String aUpVote = "vote:answer:%s:up";
    private String aDownVote = "vote:answer:%s:down";

    @RequestMapping(value = "/upVoteAnswer", method = RequestMethod.GET)
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
            logger.error(e.getMessage());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/downVoteAnswer", method = RequestMethod.GET)
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
            logger.error(e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping(value = "/up_vote_answer", params = "answer_id")
    public ResponseEntity<?> up_vote_answer(@RequestParam("answer_id") Long id) {
        AjaxResponseBody result = new AjaxResponseBody();
        try {
            if (UserAuthentication.getCurrentUser() == null) {
                result.msg = "login_require";
                return ResponseEntity.ok(result);
            }
            result.msg = "up";
            result.upCount = 25;
            result.downCount = 35;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/down_vote_answer", params = "answer_id")
    public ResponseEntity<?> down_vote_answer(@RequestParam("answer_id") Long id) {
        AjaxResponseBody result = new AjaxResponseBody();
        try {
            if (UserAuthentication.getCurrentUser() == null) {
                result.msg = "login_require";
                return ResponseEntity.ok(result);
            }
            result.msg = "down";
            result.upCount = 25;
            result.downCount = 35;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }
}

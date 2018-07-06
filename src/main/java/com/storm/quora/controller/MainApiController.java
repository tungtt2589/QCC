package com.storm.quora.controller;

import com.google.gson.Gson;
import com.storm.quora.cache.RedisCache;
import com.storm.quora.cache.redis.CacheManager;
import com.storm.quora.cache.redis.MainCache;
import com.storm.quora.config.UserAuthentication;
import com.storm.quora.dto.QuestionDTO;
import com.storm.quora.model.User;
import com.storm.quora.util.Constant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@RestController
public class MainApiController {

//    @RequestMapping(value = "/up", params = "id", method = RequestMethod.GET)
//    public Integer upVote(@RequestParam("id") int id, RedirectAttributes redirectAttributes) throws Exception {
//        return id;
//    }
}

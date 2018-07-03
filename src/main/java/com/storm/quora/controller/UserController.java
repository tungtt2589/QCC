package com.storm.quora.controller;

import com.google.gson.Gson;
import com.storm.quora.cache.RedisCache;
import com.storm.quora.cache.redis.CacheManager;
import com.storm.quora.cache.redis.MainCache;
import com.storm.quora.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(value = "/register_success", method = RequestMethod.POST)
    public ModelAndView success(@ModelAttribute UserDTO userDTO, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            CacheManager cm = RedisCache.getManager();
            if (cm != null) {
                MainCache cache = cm.getMainCache();
                cache.setValue("test", new Gson().toJson(userDTO));
                //TODO: md5 password
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}

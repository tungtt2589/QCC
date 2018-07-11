package com.storm.quora.controller;

import com.google.gson.Gson;
import com.storm.quora.cache.RedisCache;
import com.storm.quora.cache.redis.CacheManager;
import com.storm.quora.cache.redis.MainCache;
import com.storm.quora.common.AjaxResponseBody;
import com.storm.quora.common.GoogleProfile;
import com.storm.quora.config.UserAuthentication;
import com.storm.quora.dto.AnswerDTO;
import com.storm.quora.dto.QuestionDTO;
import com.storm.quora.dto.TopicDTO;
import com.storm.quora.model.User;
import com.storm.quora.service.AnswerService;
import com.storm.quora.service.QuestionService;
import com.storm.quora.service.TopicService;
import com.storm.quora.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private TopicService service;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    private List<QuestionDTO> questions;
    private String questionId = "";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(@ModelAttribute("topicid") String topicId) {
        List<TopicDTO> topics = new ArrayList<>();
        questions = new ArrayList<>();
        questions = null;
        try {
            questions = questionService.getAllQuestion();
            Collections.reverse(questions);
            topics = service.getAllTopic();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("questions", questions);
        modelAndView.addObject("topics", topics);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String test() {
        return "create";
    }

    @RequestMapping(value = "/topic", method = RequestMethod.POST)
    public String postTopic() {
        return "redirect:/";
    }

    @GetMapping(value = "/", params = "topic_id")
    public ModelAndView questionsByTopic(@RequestParam("topic_id") Long id) {
        List<QuestionDTO> questions1 = new ArrayList<>();
        List<TopicDTO> topics = new ArrayList<>();
        try {
            questions1 = questionService.getAllQuestionByTopic(id);
            Collections.reverse(questions1);
            topics = service.getAllTopic();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("questions", questions1);
        modelAndView.addObject("topics", topics);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping(value = "/view", params = "id")
    public ModelAndView detailView(@RequestParam("id") Long id) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        List<AnswerDTO> answerDTOS = new ArrayList<>();
        List<TopicDTO> topics = new ArrayList<>();
        questionId = id.toString();
        try {
            answerDTOS = answerService.getAllAnswer(id.toString());
            topics = service.getAllTopic();
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelAndView.addObject("answers", answerDTOS);
        modelAndView.addObject("topics", topics);
        modelAndView.addObject("question", questionService.getQuestionById(id));

        modelAndView.setViewName("view");
        return modelAndView;
    }

    @PostMapping("/create/save")
    public ModelAndView showPage(@ModelAttribute("createAnswer") QuestionDTO questionDTO, RedirectAttributes redirectAttributes) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        if (questionDTO.getContent().equals("")) {
            redirectAttributes.addFlashAttribute("message", "Câu hỏi không được để trống");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        } else if (questionDTO.getTopicId() == 0) {
            redirectAttributes.addFlashAttribute("message", "Chủ đề không được để trống");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        } else if (questionDTO.getTopicId() == 0 && questionDTO.getContent().equals("")) {
            redirectAttributes.addFlashAttribute("message", "Chủ đề và câu  không được để trống");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        } else {
            int i = questionService.createQuestion(questionDTO.getContent(), Long.toString(questionDTO.getTopicId()), questionDTO.getDescription());
            if (i == 1) {
                System.out.println("Success");
                redirectAttributes.addFlashAttribute("message", "Success");
                redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            } else {
                System.out.println("Not successs");
                redirectAttributes.addFlashAttribute("message", "Failed");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            }
        }

        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @PostMapping("/view/save")
    public ModelAndView createAnswer(@ModelAttribute("createAnswer") AnswerDTO answerDTO, RedirectAttributes redirectAttributes) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        if (answerDTO.getContent().equals("")) {
            redirectAttributes.addFlashAttribute("message", "Câu hỏi không được để trống");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        } else {
            int i = answerService.createAnswer(questionId, answerDTO.getContent());
            if (i == 1) {
                System.out.println("Success");
                redirectAttributes.addFlashAttribute("message", "Success");
                redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            } else {
                System.out.println("Not success");
                redirectAttributes.addFlashAttribute("message", "Failed");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            }
        }

        modelAndView.setViewName("redirect:/view?id=" + questionId);
        return modelAndView;
    }

    @GetMapping(value = "/up", params = "id")
    public ModelAndView upVote(@RequestParam("id") String id, RedirectAttributes redirectAttributes) throws Exception {
        logger.info("question id: " + id);
        ModelAndView modelAndView = new ModelAndView();
        String keyUp = String.format(Constant.UP_VOTE_QUESTION_CACHE_FORMAT, id);
        String keyDown = String.format(Constant.DOWN_VOTE_QUESTION_CACHE_FORMAT, id);
        if (UserAuthentication.getCurrentUser() == null) {
            redirectAttributes.addFlashAttribute("message", "Bạn chưa đăng nhập. Vui lòng đăng nhập để tiếp tục");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return new ModelAndView("forward:/");
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
                QuestionDTO questionDTO = questions.stream().filter(q -> q.getQuestionId() == Long.valueOf(id)).findAny().orElse(null);
//                int index = questions.indexOf(questionDTO);
                logger.info("filter: " + new Gson().toJson(questionDTO));
                logger.info("size list question: " + questions.size());
                long countUp = mc.hlen(keyUp);
                long countDown = mc.hlen(keyDown);
                if (questionDTO != null) {
                    questionDTO.setUpCount(countUp);
                    questionDTO.setDownCount(countDown);
                }
//                modelAndView.getModelMap().addAttribute("questions", questions);
                modelAndView.getModelMap().addAttribute("countup", mc.hlen(keyUp));
                modelAndView.getModelMap().addAttribute("countdown", mc.hlen(keyDown));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        modelAndView.setViewName("forward:/");
        return modelAndView;

    }

    @GetMapping(value = "/down", params = "id")
    public ModelAndView downVote(@RequestParam("id") String id, RedirectAttributes redirectAttributes) throws Exception {
        logger.info("question id: " + id);
        ModelAndView modelAndView = new ModelAndView();
        String keyUp = String.format(Constant.UP_VOTE_QUESTION_CACHE_FORMAT, id);
        String keyDown = String.format(Constant.DOWN_VOTE_QUESTION_CACHE_FORMAT, id);
        if (UserAuthentication.getCurrentUser() == null) {
            redirectAttributes.addFlashAttribute("message", "Bạn chưa đăng nhập. Vui lòng đăng nhập để tiếp tục");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return new ModelAndView("forward:/");
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
                QuestionDTO questionDTO = questions.stream().filter(q -> q.getQuestionId() == Long.valueOf(id)).findAny().orElse(null);
//                int index = questions.indexOf(questionDTO);
                logger.info("filter: " + new Gson().toJson(questionDTO));
                logger.info("size list question: " + questions.size());
                long countUp = mc.hlen(keyUp);
                long countDown = mc.hlen(keyDown);
                if (questionDTO != null) {
                    questionDTO.setUpCount(countUp);
                    questionDTO.setDownCount(countDown);
                }
                modelAndView.getModelMap().addAttribute("questions", questions);
//                modelAndView.getModelMap().addAttribute("countup", mc.hlen(keyUp));
//                modelAndView.getModelMap().addAttribute("countdown", mc.hlen(keyDown));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        modelAndView.setViewName("forward:/");
        return modelAndView;

    }

    @GetMapping(value = "/up_vote", params = "question_id")
    public ResponseEntity<?> up_vote(@RequestParam("question_id") Long id) {
        String keyUp = String.format(Constant.UP_VOTE_QUESTION_CACHE_FORMAT, id);
        String keyDown = String.format(Constant.DOWN_VOTE_QUESTION_CACHE_FORMAT, id);
        AjaxResponseBody result = new AjaxResponseBody();
        try {
            if (UserAuthentication.getCurrentUser() == null) {
                result.msg = "login_require";
                return ResponseEntity.ok(result);
            }
            result.msg = "up";
            CacheManager cm = RedisCache.getManager();
            if (cm != null) {
                MainCache mc = cm.getMainCache();
                User currentUser = UserAuthentication.getCurrentUser();
                if (mc.exists(keyDown) && mc.hexists(keyDown, String.valueOf(currentUser.getUserId()))) {
                    mc.hdel(keyDown, String.valueOf(currentUser.getUserId()));
                }
                long timeNow = new Date().getTime();
                mc.hset(keyUp, String.valueOf(currentUser.getUserId()), String.valueOf(timeNow));
                result.upCount = mc.hlen(keyUp);
                result.downCount = mc.hlen(keyDown);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/down_vote", params = "question_id")
    public ResponseEntity<?> down_vote(@RequestParam("question_id") Long id) {
        String keyUp = String.format(Constant.UP_VOTE_QUESTION_CACHE_FORMAT, id);
        String keyDown = String.format(Constant.DOWN_VOTE_QUESTION_CACHE_FORMAT, id);
        AjaxResponseBody result = new AjaxResponseBody();
        try {
            if (UserAuthentication.getCurrentUser() == null) {
                result.msg = "login_require";
                return ResponseEntity.ok(result);
            }
            result.msg = "down";
            CacheManager cm = RedisCache.getManager();
            if (cm != null) {
                MainCache mc = cm.getMainCache();
                User currentUser = UserAuthentication.getCurrentUser();
                if (mc.exists(keyUp) && mc.hexists(keyUp, String.valueOf(currentUser.getUserId()))) {
                    mc.hdel(keyUp, String.valueOf(currentUser.getUserId()));
                }
                long timeNow = new Date().getTime();
                mc.hset(keyDown, String.valueOf(currentUser.getUserId()), String.valueOf(timeNow));
                result.upCount = mc.hlen(keyUp);
                result.downCount = mc.hlen(keyDown);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    /*@GetMapping(value = "/", params = "code")
    public ModelAndView loginFacebook(@RequestParam("code") String code) {
        if (code == null || code.isEmpty()) {

        } else {
            *//*model.addAttribute("facebookProfile", facebook.userOperations().getUserProfile());
        PagedList<Post> feed = facebook.feedOperations().getFeed();
        model.addAttribute("feed", feed);*//*

     *//*String [] fields = { "id", "about", "age_range", "birthday", "context", "cover", "currency", "devices", "education", "email", "favorite_athletes", "favorite_teams", "first_name", "gender", "hometown", "inspirational_people", "installed", "install_type", "is_verified", "languages", "last_name", "link", "locale", "location", "meeting_for", "middle_name", "name", "name_format", "political", "quotes", "payment_pricepoints", "relationship_status", "religion", "security_settings", "significant_other", "sports", "test_group", "timezone", "third_party_id", "updated_time", "verified", "video_upload_limits", "viewer_can_send_gift", "website", "work"};
            User user = facebook.fetchObject("me", User.class, fields);*//*


            try {
                String accessToken = "";
                accessToken = RestFB.getToken(code);
                *//*User user = RestFB.getUserInfo(accessToken);*//*
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ModelAndView modelAndView = new ModelAndView();
        *//*modelAndView.addObject("questions", questions1);
        modelAndView.addObject("topics", topics);*//*
        modelAndView.setViewName("index");
        return modelAndView;
    }*/

}

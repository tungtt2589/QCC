package com.storm.quora.controller;

import com.storm.quora.dto.AnswerDTO;
import com.storm.quora.dto.QuestionDTO;
import com.storm.quora.dto.TopicDTO;
import com.storm.quora.service.AnswerService;
import com.storm.quora.service.QuestionService;
import com.storm.quora.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class MainController {
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
}

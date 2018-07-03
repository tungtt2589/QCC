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

    //    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public ModelAndView showPersonsPages(@RequestParam("page") Optional<Integer> page) {
//        ModelAndView modelAndView = new ModelAndView("index");
//        List<Topic> topics = service.getAllTopic();
//        modelAndView.addObject("topics", topics);
//        return modelAndView;
//    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(@ModelAttribute("topicid") String topicId) {
        List<TopicDTO> topics = null;
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

//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String indexSelect(@RequestParam("topicid") String topicId) {
//        topicId = topicId;
//        System.out.printf("asfafc "+topicId);
//        return "redirect:/";
//    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String test() {
        return "create";
    }

    @RequestMapping(value = "/topic", method = RequestMethod.POST)
    public String postTopic() {
        return "redirect:/";
    }
//    @GetMapping("/auctionForm/detail/{id}")
//    public String detailView(@PathVariable("id") Long id, Model model) throws Exception {

    //        List<QuestionDTO> questions = null;
//        try {
//            questions = questionService.getAllQuestion();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        model.addAttribute("questions", questions);

//    @RequestMapping(value = "/view", method = RequestMethod.GET)
//    public String viewTopic(Model model) {
//        List<AnswerDTO> answerDTOS = null;
//        try {
//            answerDTOS = answerService.getAllAnswer("1");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        model.addAttribute("answers", answerDTOS);
//        return "view";
//    }view

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
//        System.out.println("sdvfsdvsdvdv " + id);
        ModelAndView modelAndView = new ModelAndView();
        List<AnswerDTO> answerDTOS = null;
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


    //    @GetMapping("/create/save")
//    public String showPage(Model model) {
//        model.addAttribute("createQuestion", new QuestionDTO()); //assume SomeBean has a property called datePlanted
//        return "create";
//    }
//
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
//        System.out.println("Date planted: " + bean.getContent()); //in reality, you'd use a logger instead :)
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
                System.out.println("Not successs");
                redirectAttributes.addFlashAttribute("message", "Failed");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            }
        }

        modelAndView.setViewName("redirect:/view?id=" + questionId);
//        System.out.println("Date planted: " + bean.getContent()); //in reality, you'd use a logger instead :)
        return modelAndView;
    }
}

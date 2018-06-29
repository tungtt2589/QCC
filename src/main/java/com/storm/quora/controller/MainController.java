package com.storm.quora.controller;

import com.storm.quora.domain.Pager;
import com.storm.quora.domain.Person;
import com.storm.quora.dto.AnswerDTO;
import com.storm.quora.dto.QuestionDTO;
import com.storm.quora.dto.TopicDTO;
import com.storm.quora.model.Question;
import com.storm.quora.model.Topic;
import com.storm.quora.service.AnswerService;
import com.storm.quora.service.PersonService;
import com.storm.quora.service.QuestionService;
import com.storm.quora.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            topics = service.getAllTopic();
        } catch (Exception e) {
            e.printStackTrace();
        }
        topicId = topicId;
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

    @GetMapping(value = "/view", params = "id")
    public ModelAndView detailView(@RequestParam("id") Long id, Model model) throws Exception {
//        System.out.println("sdvfsdvsdvdv " + id);
        ModelAndView modelAndView = new ModelAndView();
        List<AnswerDTO> answerDTOS = null;
        questionId = id.toString();
        try {
            answerDTOS = answerService.getAllAnswer(id.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelAndView.addObject("answers", answerDTOS);
        QuestionDTO questionDTO = questionService.getQuestionById(id);
        modelAndView.addObject("question", questionDTO);

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
    public ModelAndView showPage(@ModelAttribute("createAnswer") QuestionDTO questionDTO) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        int i = questionService.createQuestion(questionDTO.getContent(), Long.toString(questionDTO.getTopicId()), questionDTO.getDescription());
        if (i == 1){
            System.out.println("Success");
        }else {
            System.out.println("Not successs");
        }
        modelAndView.setViewName("redirect:/");
//        System.out.println("Date planted: " + bean.getContent()); //in reality, you'd use a logger instead :)
        return modelAndView;
    }

    @PostMapping("/view/save")
    public ModelAndView createAnswer(@ModelAttribute("createAnswer") AnswerDTO answerDTO) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        int i = answerService.createAnswer(questionId, answerDTO.getContent());
        if (i == 1){
            System.out.println("Success");
        }else {
            System.out.println("Not successs");
        }
        modelAndView.setViewName("redirect:/view?id=" + questionId);
//        System.out.println("Date planted: " + bean.getContent()); //in reality, you'd use a logger instead :)
        return modelAndView;
    }
}

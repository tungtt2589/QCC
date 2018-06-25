package com.storm.quora.controller;

import com.storm.quora.domain.Pager;
import com.storm.quora.domain.Person;
import com.storm.quora.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/person")
public class PersonController {
    private static final int BUTTONS_TO_SHOW = 5;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 20;
    private static final int[] PAGE_SIZES = {5, 10, 20};

    @Autowired
    PersonService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showPersonsPages(@RequestParam("page") Optional<Integer> page) {
        ModelAndView modelAndView = new ModelAndView("test");

        int evalPageSize = INITIAL_PAGE_SIZE;
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Person> persons = service.findAlPageable(PageRequest.of(evalPage, INITIAL_PAGE_SIZE));
        System.out.println(persons.getContent().size());

        Pager pager = new Pager(persons.getTotalPages(), persons.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("persons", persons);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);
        return modelAndView;
    }
}

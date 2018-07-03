package com.storm.quora.controller;

import com.google.gson.Gson;
import com.storm.quora.dto.UserDTO;
import com.storm.quora.service.UserService;
import com.storm.quora.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(value = "/register_success", method = RequestMethod.POST)
    public ModelAndView success(@ModelAttribute UserDTO userDTO, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/register");
        try {
            if (userService.findUser(userDTO.getUsername()) != null) {
                redirectAttributes.addFlashAttribute("message", "User đã tồn tại!");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            }
//                userDTO.setPassword(Utils.md5(userDTO.getPassword()));
            if (userService.register(userDTO) != null) {
                modelAndView.setViewName("redirect:/");
            }

        } catch (Exception e) {
            logger.error("fail: " + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "Có lỗi xảy ra. Vui lòng thử lại sau!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }
        return modelAndView;
    }

    //    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public ModelAndView preLogin() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("login");
//        return modelAndView;
//    }
    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @RequestMapping(value = "/login_success", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute UserDTO userDTO, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/login");
        try {
//            userDTO.setPassword(Utils.md5(userDTO.getPassword()));
            if (userService.login(userDTO) != null) {
                modelAndView.setViewName("redirect:/");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("message", "Có lỗi xảy ra. Vui lòng thử lại sau!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public ModelAndView userInfo(Principal principal) {
        String username = principal.getName();
        logger.info("username: " + username);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}

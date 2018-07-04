package com.storm.quora.controller;

import com.google.gson.Gson;
import com.storm.quora.config.UserAuthentication;
import com.storm.quora.dto.UserDTO;
import com.storm.quora.model.User;
import com.storm.quora.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/registerForm", method = RequestMethod.GET)
    public ModelAndView getFormRegister() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView success(@ModelAttribute UserDTO userDTO, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/register");
        try {
            if (userService.findByName(userDTO.getUsername()) != null) {
                redirectAttributes.addFlashAttribute("message", "User đã tồn tại!");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            }
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//            userDTO.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getFormLogin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public ModelAndView userInfo(Principal principal) throws Exception{
        Object principal1 = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name;
        if (principal1 instanceof UserDetails) {
            name = ((UserDetails) principal1).getUsername();
        } else {
            name = principal1.toString();
        }
        String username = principal.getName();
        User user = UserAuthentication.getCurrentUser();
        logger.info(new Gson().toJson(user));
        logger.info("username: " + name);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userInfo");
        return modelAndView;
    }

}

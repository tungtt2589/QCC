package com.storm.quora.controller;

import com.google.gson.Gson;
import com.storm.quora.common.validator.UserValidator;
import com.storm.quora.config.UserAuthentication;
import com.storm.quora.dto.UserDTO;
import com.storm.quora.model.User;
import com.storm.quora.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired
    private UserAuthentication userAuthentication;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/registerForm", method = RequestMethod.GET)
    public ModelAndView getFormRegister() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView success(@ModelAttribute("userDTO") UserDTO userDTO, RedirectAttributes redirect) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");

//        userValidator.validate(userDTO, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return new ModelAndView("register");
//        }
        if (userDTO.getName() == null || userDTO.getName().equals("")) {
            redirect.addFlashAttribute("message", "Bạn chưa nhập họ tên");
            redirect.addFlashAttribute("alertClass", "alert-danger");
            return new ModelAndView("redirect:/registerForm");
        }
        if (userDTO.getUsername() == null || userDTO.getUsername().equals("")) {
            redirect.addFlashAttribute("message", "Bạn chưa nhập tên đăng nhập");
            redirect.addFlashAttribute("alertClass", "alert-danger");
            return new ModelAndView("redirect:/registerForm");
        }
        if (userDTO.getPassword() == null || userDTO.getPassword().equals("")) {
            redirect.addFlashAttribute("message", "Bạn chưa nhập mật khẩu");
            redirect.addFlashAttribute("alertClass", "alert-danger");
            return new ModelAndView("redirect:/registerForm");
        }
        if (!userDTO.getPassword().equals(userDTO.getPasswordConfirm())) {
            redirect.addFlashAttribute("message", "Mật khẩu xác nhận không chính xác");
            redirect.addFlashAttribute("alertClass", "alert-danger");
            return new ModelAndView("redirect:/registerForm");
        }
        try {
            if (userService.findByName(userDTO.getUsername()) != null) {
                modelAndView.addObject("message", "User đã tồn tại!");
                modelAndView.addObject("alertClass", "alert-danger");
                return new ModelAndView("redirect:/registerForm");
            }
            String passOriginal = userDTO.getPassword();
            userDTO.setPassword(new BCryptPasswordEncoder().encode(passOriginal));

            if (userService.register(userDTO) != null) {
                userAuthentication.autoLogin(userDTO.getUsername(),passOriginal);
                return new ModelAndView("redirect:/");
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", "Có lỗi xảy ra. Vui lòng thử lại sau!");
            modelAndView.addObject("alertClass", "alert-danger");
            return new ModelAndView("redirect:/registerForm");
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
    public ModelAndView userInfo(Principal principal) throws Exception {
        Object principal1 = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name;
        if (principal1 instanceof UserDetails) {
            name = ((UserDetails) principal1).getUsername();
        } else {
            name = principal1.toString();
        }
        logger.info("username: " + name);
        User user = UserAuthentication.getCurrentUser();
        logger.info(new Gson().toJson(user));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user",user);
        modelAndView.setViewName("userInfo");
        return modelAndView;
    }

    @PostMapping("/userInfo/update")
    public ModelAndView showPage(@ModelAttribute("updateUser") UserDTO userDTO, RedirectAttributes redirectAttributes) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
//        if (questionDTO.getContent().equals("")) {
//            redirectAttributes.addFlashAttribute("message", "Câu hỏi không được để trống");
//            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
//        } else if (questionDTO.getTopicId() == 0) {
//            redirectAttributes.addFlashAttribute("message", "Chủ đề không được để trống");
//            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
//        } else if (questionDTO.getTopicId() == 0 && questionDTO.getContent().equals("")) {
//            redirectAttributes.addFlashAttribute("message", "Chủ đề và câu  không được để trống");
//            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
//        } else {
            User user = UserAuthentication.getCurrentUser();
            int i = userService.updateUser(String.valueOf(user.getUserId()),userDTO.getName(), userDTO.getEmail(),userDTO.getPhoneNumber(),userDTO.getDob(),userDTO.getGender(),userDTO.getAddress(),userDTO.getJob());
            if (i == 1) {
                System.out.println("Success");
                redirectAttributes.addFlashAttribute("message", "Success");
                redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            } else {
                System.out.println("Not successs");
                redirectAttributes.addFlashAttribute("message", "Failed");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            }
//        }

        modelAndView.setViewName("redirect:/userInfo");
        return modelAndView;
    }

}

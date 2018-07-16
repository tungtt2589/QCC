package com.storm.quora.controller;

import com.storm.quora.common.AjaxResponseBody;
import com.storm.quora.common.GoogleProfile;
import com.storm.quora.dto.UserDTO;
import com.storm.quora.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RestWebController {
    private static final Logger logger = LoggerFactory.getLogger(RestWebController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login_google", method = RequestMethod.POST)
    public ResponseEntity<?> loginGoogle(@RequestBody GoogleProfile profile, HttpServletRequest request) {
        AjaxResponseBody result = new AjaxResponseBody();
        result.msg = "Access Token: " + profile.getAccess_token();
        result.msg = "Email: " + profile.getEmail();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetails = new User(profile.getEmail(), "", true, true, true, true, authorities);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        // create account if not exist
        try {
            if (null == userService.findByName(profile.getEmail())) {
                UserDTO user = new UserDTO();
                user.setName(profile.getDisplay_name());
                user.setPassword("");
                user.setUsername(profile.getEmail());
                user.setAvatar(profile.getImage_url());
                user.setEmail(profile.getEmail());
                user.setGoogleId(profile.getId());
                if (userService.register(user) != null) {
                    logger.info("Login google ok");
                }
            }
        } catch (Exception e) {
            logger.error("Error when create user by google account; " + e.getMessage());
        }
        return ResponseEntity.ok(result);
    }
}

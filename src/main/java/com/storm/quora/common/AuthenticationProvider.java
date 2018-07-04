package com.storm.quora.common;

import com.storm.quora.config.ServiceContainer;
import com.storm.quora.model.User;
import com.storm.quora.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProvider {
    @Autowired
    UserService userService;

    public User getUserByName(String username) throws Exception{
        if (username == null || username.length() == 0) {
            return null;
        } else {
            return ServiceContainer.userService.findByName(username);
        }
    }

}

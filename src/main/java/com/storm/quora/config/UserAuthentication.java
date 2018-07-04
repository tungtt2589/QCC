package com.storm.quora.config;

import com.storm.quora.common.ApplicationContextProvider;
import com.storm.quora.common.AuthenticationProvider;
import com.storm.quora.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserAuthentication {
    public static User getCurrentUser() throws Exception{

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        AuthenticationProvider authenticationProvider = ApplicationContextProvider.getBean("authenticationProvider", AuthenticationProvider.class);

        return authenticationProvider.getUserByName(auth.getName());
    }
}

package com.storm.quora.config;

import com.storm.quora.common.ApplicationContextProvider;
import com.storm.quora.common.AuthenticationProvider;
import com.storm.quora.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Controller
public class UserAuthentication {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthentication.class);

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService userDetailsService;

    public static User getCurrentUser() throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        AuthenticationProvider authenticationProvider = ApplicationContextProvider.getBean("authenticationProvider", AuthenticationProvider.class);

        return authenticationProvider.getUserByName(auth.getName());
    }

    public void autoLogin(String username, String password) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

            Authentication auth = authenticationManager.authenticate(token);

            if (token.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(auth);
                logger.debug(String.format("Auto login %s successfully!", username));
            }
        } catch (AuthenticationException e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            logger.error("Login failed after registration. This should never happen: ", e);
        }

    }

}

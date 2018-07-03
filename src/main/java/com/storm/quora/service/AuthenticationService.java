package com.storm.quora.service;

import com.storm.quora.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = userService.findUser(username);

        if (userDTO == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the db");
        }

        long role = userDTO.getRole();

        List<GrantedAuthority> grantList = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(role == 0 ? "ADMIN" : "USER");
        grantList.add(authority);

        UserDetails userDetails = new User(userDTO.getUsername(), userDTO.getPassword(), grantList);
        return userDetails;
    }
}

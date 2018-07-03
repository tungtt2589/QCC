package com.storm.quora.service;

import com.storm.quora.dto.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Override
    public UserDTO findUser(String username) {
        return null;
    }
}

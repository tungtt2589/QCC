package com.storm.quora.service;

import com.storm.quora.dto.UserDTO;
import com.storm.quora.model.User;

public interface UserService {
    User findByName(String username) throws Exception;

    UserDTO register(UserDTO userDTO) throws Exception;
}

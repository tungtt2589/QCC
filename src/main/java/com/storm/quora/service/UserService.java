package com.storm.quora.service;

import com.storm.quora.dto.UserDTO;

public interface UserService {
    UserDTO findUser(String username) throws Exception;

    UserDTO register(UserDTO userDTO) throws Exception;

    UserDTO login(UserDTO userDTO) throws Exception;
}

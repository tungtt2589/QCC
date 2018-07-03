package com.storm.quora.service;

import com.storm.quora.dto.UserDTO;

public interface UserService {
    UserDTO findUser(String username);
}

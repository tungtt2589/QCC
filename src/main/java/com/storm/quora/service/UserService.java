package com.storm.quora.service;

import com.storm.quora.dto.UserDTO;
import com.storm.quora.model.User;

public interface UserService {
    User findByName(String username) throws Exception;

    UserDTO register(UserDTO userDTO) throws Exception;

    int updateUser(String userId, String name, String email, String phone, String dob, String gender, String address, String job) throws Exception;

}

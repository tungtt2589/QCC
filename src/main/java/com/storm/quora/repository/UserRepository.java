package com.storm.quora.repository;

import com.storm.quora.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String username);
}

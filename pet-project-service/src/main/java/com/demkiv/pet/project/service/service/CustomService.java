package com.demkiv.pet.project.service.service;

import com.demkiv.pet.project.service.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface CustomService {
    String login(String username, String password);
    Optional<User> findByToken(String token);
    UserDetails getUserDetails(String token);
}

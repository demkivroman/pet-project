package com.demkiv.pet.project.service.service;

import java.util.Optional;

public interface CustomService {
    String login(String username, String password);
    Optional<org.springframework.security.core.userdetails.User> findByToken(String token);
}

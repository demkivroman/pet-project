package com.demkiv.pet.project.service.service.security;

import com.demkiv.pet.project.service.entity.security.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Optional;

public interface CustomService {
    String login(String username, String password);
    Optional<User> findByToken(String token);
    UserDetails getUserDetails(String token);
    Collection<? extends GrantedAuthority> getAuthorities(String token);
}

package com.demkiv.pet.project.service.service.security.impl;

import com.demkiv.pet.project.service.entity.security.User;
import com.demkiv.pet.project.service.repository.security.RolePrivilegeRepository;
import com.demkiv.pet.project.service.repository.security.UserRepository;
import com.demkiv.pet.project.service.service.security.AuthoritiesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class AuthoritiesServiceImp implements AuthoritiesService {
    private final UserRepository userRepository;
    private final RolePrivilegeRepository rolePrivilegeRepository;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
        log.info(String.format("User %s is saved to database.", user));
    }

    @Override
    public User findUser(String user) {
        return userRepository.findByName(user)
                .orElseThrow();
    }

    @Override
    public List<String> getAllPrivilegesByRoleName(String roleName) {
        return rolePrivilegeRepository.getAllPrivilegesByRoleName(roleName);
    }
}

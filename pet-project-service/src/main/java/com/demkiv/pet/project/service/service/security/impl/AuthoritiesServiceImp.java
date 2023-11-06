package com.demkiv.pet.project.service.service.security.impl;

import com.demkiv.pet.project.service.entity.security.Privilege;
import com.demkiv.pet.project.service.entity.security.Role;
import com.demkiv.pet.project.service.entity.security.User;
import com.demkiv.pet.project.service.repository.security.PrivilegeRepository;
import com.demkiv.pet.project.service.repository.security.RoleRepository;
import com.demkiv.pet.project.service.repository.security.UserRepository;
import com.demkiv.pet.project.service.service.security.AuthoritiesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class AuthoritiesServiceImp implements AuthoritiesService {
    private final UserRepository userRepository;
    private final PrivilegeRepository privilegeRepository;
    private final RoleRepository roleRepository;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
        log.info(String.format("User %s is saved to database.", user));
    }

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
        log.info(String.format("Role %s is saved to database.", role));
    }

    @Override
    public void savePrivilege(Privilege privilege) {
        privilegeRepository.save(privilege);
        log.info(String.format("Privilege %s is saved to database.", privilege));
    }

    @Override
    public User findUser(String user) {
        return userRepository.findByName(user)
                .orElseThrow();
    }

    @Override
    public Role findRoleByName(String roleName) {
        return roleRepository.findByNameNative(roleName);
    }
}

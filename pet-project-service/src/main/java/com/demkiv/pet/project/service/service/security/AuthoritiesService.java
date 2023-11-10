package com.demkiv.pet.project.service.service.security;

import com.demkiv.pet.project.service.entity.security.User;
import com.demkiv.pet.project.service.entity.security.UserAuthorities;

import java.util.List;

public interface AuthoritiesService {
    void saveUser(User user);
    void saveUserAuthority(UserAuthorities authorities, User user);
    User findUser(String user);
    List<String> getAllPrivilegesByRoleName(String roleName);
}

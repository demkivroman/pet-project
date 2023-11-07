package com.demkiv.pet.project.service.service.security;

import com.demkiv.pet.project.service.entity.security.Privilege;
import com.demkiv.pet.project.service.entity.security.Role;
import com.demkiv.pet.project.service.entity.security.User;

import java.util.List;

public interface AuthoritiesService {
    void saveUser(User user);
    User findUser(String user);
    List<String> getAllPrivilegesByRoleName(String roleName);
}

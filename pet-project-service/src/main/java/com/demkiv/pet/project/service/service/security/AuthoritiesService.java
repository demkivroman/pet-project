package com.demkiv.pet.project.service.service.security;

import com.demkiv.pet.project.service.entity.security.Privilege;
import com.demkiv.pet.project.service.entity.security.Role;
import com.demkiv.pet.project.service.entity.security.User;

public interface AuthoritiesService {
    void saveUser(User user);
    void saveRole(Role role);
    void savePrivilege(Privilege privilege);
    User findUser(String user);
    Role findRoleByName(String roleName);
}

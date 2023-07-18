package com.demkiv.pet.project.service.security;

import com.demkiv.pet.project.service.entity.security.Privilege;
import com.demkiv.pet.project.service.entity.security.Role;

import java.util.Collection;

public interface AuthorityInterface {
    Privilege createPrivilegeIfNotFound(String name);
    Role createRoleIfNotFound(String name, Collection<Privilege> privileges);
}

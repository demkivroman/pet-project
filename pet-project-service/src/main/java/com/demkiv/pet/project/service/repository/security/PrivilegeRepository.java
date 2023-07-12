package com.demkiv.pet.project.service.repository.security;

import com.demkiv.pet.project.service.entity.security.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String name);
}

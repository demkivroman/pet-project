package com.demkiv.pet.project.service.repository.security;

import com.demkiv.pet.project.service.entity.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(
            value = "SELECT id, name FROM role WHERE name = 'ADMIN'",
            nativeQuery = true)
    Role findByNameNative(String name);
    @Override
    <S extends Role> S save(S entity);
}

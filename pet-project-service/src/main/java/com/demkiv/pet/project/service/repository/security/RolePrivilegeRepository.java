package com.demkiv.pet.project.service.repository.security;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RolePrivilegeRepository {
    private final JdbcTemplate jdbcTemplate;

    private static String GET_PRIVILEGES_QUERY = "select p.name from privilege as p\n" +
            "inner join roles_privileges as rp on p.ID = rp.PRIVILEGE_ID\n" +
            "inner join role r on r.ID = rp.ROLE_ID where r.NAME = ?";

    public List<String> getAllPrivilegesByRoleName(String roleName) {
        return jdbcTemplate.queryForList(GET_PRIVILEGES_QUERY, String.class, roleName);
    }
}

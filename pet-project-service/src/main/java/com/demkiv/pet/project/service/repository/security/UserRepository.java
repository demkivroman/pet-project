package com.demkiv.pet.project.service.repository.security;

import com.demkiv.pet.project.service.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT u FROM User u where u.name = ?1 and u.password = ?2 ")
    Optional<User> login(String username, String password);
    Optional<User> findByNameAndPassword(String username, String password);
    Optional<User> findByToken(String token);
}

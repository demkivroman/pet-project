package com.demkiv.pet.project.service.repository;

import com.demkiv.pet.project.service.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<User, Long> {
    @Query(value = "SELECT u FROM User u where u.name = ?1 and u.password = ?2 ")
    Optional<User> login(String username, String password);
    Optional<User> findByNameAndPassword(String username, String password);
    Optional<User> findByToken(String token);
}

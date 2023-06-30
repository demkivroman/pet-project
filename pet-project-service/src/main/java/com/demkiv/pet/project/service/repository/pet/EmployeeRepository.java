package com.demkiv.pet.project.service.repository.pet;


import com.demkiv.pet.project.service.entity.pet.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    @Override
    <S extends Employee> S save(S entity);
    Optional<Employee> findById(String id);
    @Override
    void delete(Employee entity);
    @Override
    List<Employee> findAll();
    void deleteById(String id);
}

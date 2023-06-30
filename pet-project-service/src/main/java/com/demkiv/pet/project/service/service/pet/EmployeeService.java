package com.demkiv.pet.project.service.service.pet;

import com.demkiv.pet.project.service.entity.pet.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    void saveEmployee(Employee employee);
    Optional<Employee> getEmployee(String id);
    void deleteEmployee(String id);
    Optional<Employee> updateEmployee(Employee employee);
}

package com.demkiv.pet.project.service.service.pet.impl;

import com.demkiv.pet.project.service.entity.pet.Employee;
import com.demkiv.pet.project.service.repository.pet.EmployeeRepository;
import com.demkiv.pet.project.service.service.pet.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository repository;

    @Override
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @Override
    public void saveEmployee(Employee employee) {
        String id = UUID.randomUUID().toString();
        employee.setId(id);
        log.debug("Saving employee: " + employee);
        repository.save(employee);
    }

    @Override
    public Optional<Employee> getEmployee(String id) {
        return repository.findById(id);
    }

    @Override
    public void deleteEmployee(String id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Employee> updateEmployee(Employee employee) {
        Optional<Employee> foundEmployee = repository.findById(employee.getId());
        log.debug("Updating employee - " + foundEmployee);
        if (foundEmployee.isPresent()) {
            Employee updatedEmployee = foundEmployee.get();
            updatedEmployee.setId(employee.getId());
            updatedEmployee.setName(employee.getName());
            updatedEmployee.setFirstName(employee.getFirstName());
            updatedEmployee.setBirthDate(employee.getBirthDate());
            updatedEmployee.setSalary(employee.getSalary());
            updatedEmployee.setPosition(employee.getPosition());
            updatedEmployee.setEmail(employee.getEmail());
            updatedEmployee.setExperience(employee.getExperience());

            repository.save(updatedEmployee);
            log.debug(String.format("Employee with id - %s is updated. New employee is %s", employee.getId(), updatedEmployee));
            return Optional.of(updatedEmployee);
        }
        return Optional.empty();
    }
}

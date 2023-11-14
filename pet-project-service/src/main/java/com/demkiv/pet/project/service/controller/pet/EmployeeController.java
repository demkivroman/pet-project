package com.demkiv.pet.project.service.controller.pet;

import com.demkiv.pet.project.service.entity.pet.Employee;
import com.demkiv.pet.project.service.models.ResponseEnum;
import com.demkiv.pet.project.service.service.pet.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.demkiv.pet.project.service.models.ResponseEnum.*;

@Slf4j
@RestController
@AllArgsConstructor
public class EmployeeController {
    private EmployeeService service;

    @PostMapping(value = "api/add/employee",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseEnum> addEmployee(@RequestBody @Valid Employee employee) {
        service.saveEmployee(employee);
        log.debug("Employee is saved. Id - " + employee.getId());
        return ResponseEntity.of(Optional.of(SUCCESS));
    }

    @GetMapping(value = "api/all/employees",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = service.getAllEmployees();
        log.debug("All employees " + employees);
        return ResponseEntity.of(Optional.of(employees));
    }

    @PostMapping(value = "api/update/employee",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseEnum> updateEmployee(@Valid @RequestBody Employee employee) {
        service.updateEmployee(employee);
        return ResponseEntity.of(Optional.of(SUCCESS));
    }

    @PostMapping(value = "api/delete/employee")
    public ResponseEntity<ResponseEnum> deleteEmployee(@RequestParam String id) {
        service.deleteEmployee(id);
        log.debug(String.format("Employee with id - %s is deleted from database", id));
        return ResponseEntity.of(Optional.of(SUCCESS));
    }
}

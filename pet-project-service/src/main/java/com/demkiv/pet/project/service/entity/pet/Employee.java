package com.demkiv.pet.project.service.entity.pet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @NotNull(message = "Name cannot be null")
    @NotBlank
    @Size(min = 3, max = 200, message = "Name should be more the three characters")
    private String name;
    @Column(name = "firstname")
    @NotNull(message = "Firstname cannot be null")
    @NotBlank
    @Size(min = 3, max = 20, message = "FirstName should be more the three characters")
    private String firstName;
    @Column(name = "birthdate")
    @Past
    private Date birthDate;
    @NotNull(message = "Position cannot be null")
    @NotBlank
    @Size(min = 3, max = 200, message = "Position should be more the three characters")
    private String position;
    @Positive
    private float salary;
    @PositiveOrZero
    private float experience;
    @NotNull
    @Email(message = "Email should be valid")
    private String email;
}

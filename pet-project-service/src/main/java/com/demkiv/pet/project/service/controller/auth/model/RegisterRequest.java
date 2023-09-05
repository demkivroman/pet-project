package com.demkiv.pet.project.service.controller.auth.model;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterRequest {
    private String name;
    private String password;
    private Role roles;
    private Privilege privileges;
}

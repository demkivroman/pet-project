package com.demkiv.pet.project.service.controller.auth.model;


import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterRequest {
    private String name;
    private String password;
    private RoleModel role;
}

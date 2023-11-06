package com.demkiv.pet.project.service.service.security.impl;

import com.demkiv.pet.project.service.controller.auth.model.AuthenticationRequest;
import com.demkiv.pet.project.service.controller.auth.model.AuthenticationResponse;
import com.demkiv.pet.project.service.controller.auth.model.PrivilegeModel;
import com.demkiv.pet.project.service.controller.auth.model.RegisterRequest;
import com.demkiv.pet.project.service.entity.security.Privilege;
import com.demkiv.pet.project.service.entity.security.Role;
import com.demkiv.pet.project.service.entity.security.User;
import com.demkiv.pet.project.service.security.JwtService;
import com.demkiv.pet.project.service.service.security.AuthenticationService;
import com.demkiv.pet.project.service.service.security.AuthoritiesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthoritiesService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse register(RegisterRequest request) {
//        role.setPrivileges(request.getPrivileges()
//                .stream()
//                .map(PrivilegeModel::getName)
//                .map(this::createAndSavePrivilege)
//                .collect(Collectors.toList()));
//        role.setName(request.getRole().getName());
        log.debug("before getting role ");
        Role role = authenticationService.findRoleByName(request.getRole().getName());
        log.debug("role " + role);
        User user = new User();
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(List.of(role));
        authenticationService.saveUser(user);
        String jwtToken = jwtService.generateJwtToken(user);

        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword()));

        var user = authenticationService.findUser(request.getName());
        var jwtToken = jwtService.generateJwtToken(user);
        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }

    private Privilege createAndSavePrivilege(String name) {
        Privilege privilege = new Privilege();
        privilege.setName(name);
        return  privilege;
    }
}

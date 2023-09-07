package com.demkiv.pet.project.service.service.security;

import com.demkiv.pet.project.service.controller.auth.model.AuthenticationRequest;
import com.demkiv.pet.project.service.controller.auth.model.AuthenticationResponse;
import com.demkiv.pet.project.service.controller.auth.model.Privilege;
import com.demkiv.pet.project.service.controller.auth.model.RegisterRequest;
import com.demkiv.pet.project.service.entity.security.Role;
import com.demkiv.pet.project.service.entity.security.User;
import com.demkiv.pet.project.service.repository.security.PrivilegeRepository;
import com.demkiv.pet.project.service.repository.security.RoleRepository;
import com.demkiv.pet.project.service.repository.security.UserRepository;
import com.demkiv.pet.project.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PrivilegeRepository privilegeRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse register(RegisterRequest request) {
        Role role = new Role();
        role.setPrivileges(request.getPrivileges()
                .stream()
                .map(Privilege::getName)
                .map(this::createAndSavePrivilege)
                .collect(Collectors.toList()));
        role.setName(request.getRoles().getName());
        roleRepository.save(role);
        User user = new User();
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(List.of(role));
        userRepository.save(user);
        var jwtToken = jwtService.generateJwtToken(user);

        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword()));

        var user = userRepository.findByName(request.getName())
                .orElseThrow();
        var jwtToken = jwtService.generateJwtToken(user);
        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }

    private com.demkiv.pet.project.service.entity.security.Privilege createAndSavePrivilege(String name) {
        com.demkiv.pet.project.service.entity.security.Privilege privilege =
                new com.demkiv.pet.project.service.entity.security.Privilege();
        privilege.setName(name);
        privilegeRepository.save(privilege);
        return  privilege;
    }
}

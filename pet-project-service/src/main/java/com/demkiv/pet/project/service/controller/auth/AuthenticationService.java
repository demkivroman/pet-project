package com.demkiv.pet.project.service.controller.auth;

import com.demkiv.pet.project.service.controller.auth.model.RegisterRequest;
import com.demkiv.pet.project.service.repository.security.UserRepository;
import com.demkiv.pet.project.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationResponse register(RegisterRequest request) {
        System.out.println(request);
        return null;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword()));

        var user = repository.findByName(request.getName())
                .orElseThrow();
        var jwtToken = jwtService.generateJwtToken(user);
        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }
}

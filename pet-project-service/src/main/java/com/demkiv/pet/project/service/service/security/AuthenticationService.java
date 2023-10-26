package com.demkiv.pet.project.service.service.security;

import com.demkiv.pet.project.service.controller.auth.model.AuthenticationRequest;
import com.demkiv.pet.project.service.controller.auth.model.AuthenticationResponse;
import com.demkiv.pet.project.service.controller.auth.model.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}

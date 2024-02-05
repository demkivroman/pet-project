package com.demkiv.pet.project.service.service.security.impl;

import com.demkiv.pet.project.service.controller.auth.model.AuthenticationRequest;
import com.demkiv.pet.project.service.controller.auth.model.AuthenticationResponse;
import com.demkiv.pet.project.service.controller.auth.model.RegisterRequest;
import com.demkiv.pet.project.service.entity.security.User;
import com.demkiv.pet.project.service.entity.security.UserAuthorities;
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
        String roleName = request.getRole().getName();
        List<String> privileges = authenticationService.getAllPrivilegesByRoleName(roleName);
        List<UserAuthorities> userAuthorities = convertToUserAuthorities(List.of(roleName), true);
        userAuthorities.addAll(convertToUserAuthorities(privileges, false));
        User user = new User();
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userAuthorities.forEach(user::addUserAuthority);
        authenticationService.saveUser(user);
        String jwtToken = jwtService.generateJwtToken(user);

        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword()));
        log.debug("User {} is authenticated.", request.getName());

        var user = authenticationService.findUser(request.getName());
        var jwtToken = jwtService.generateJwtToken(user);
        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }

    private List<UserAuthorities> convertToUserAuthorities(List<String> authorities, boolean isRole) {
        return (isRole) ? authorities.stream()
                .map(role -> "ROLE_" + role)
                .map(role -> {
                    UserAuthorities authority = new UserAuthorities();
                    authority.setName(role);
                    return authority;
                }).collect(Collectors.toList()) :
                authorities.stream()
                        .map(auth -> {
                            UserAuthorities authority = new UserAuthorities();
                            authority.setName(auth);
                            return authority;
                        }).collect(Collectors.toList());
    }
}

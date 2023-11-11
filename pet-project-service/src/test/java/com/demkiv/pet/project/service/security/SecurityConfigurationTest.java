package com.demkiv.pet.project.service.security;

import com.demkiv.pet.project.service.repository.security.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SecurityConfiguration.class)
public class SecurityConfigurationTest {
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    private AuthenticationProvider internalProvider;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private SecurityFilterChain filterChain;

    @Autowired
    private SecurityConfiguration config;

    @Test
    void shouldCreateConfig() {
        assertThat(config).isNotNull();
    }
}

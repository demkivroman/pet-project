package com.demkiv.pet.project.service.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ComponentScan("com.demkiv.pet.project.service.security")
@AllArgsConstructor
public class SecurityConfiguration {
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider provider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .and()
                .authorizeHttpRequests(request -> request.requestMatchers("/token").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/add/employee")
                        .hasRole("ADMIN")
                        .requestMatchers("api/all/employees")
                        .hasAuthority("READ_PRIVILEGE")
                        .requestMatchers("api/update/employee")
                        .hasAuthority("WRITE_PRIVILEGE")
                        .requestMatchers("api/delete/employee")
                        .hasAuthority("DELETE_PRIVILEGE")
                        .anyRequest().authenticated())
                .authenticationProvider(provider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .formLogin()
                .disable()
                .httpBasic()
                .disable()
                .logout()
                .disable()
                .build();

    }
}

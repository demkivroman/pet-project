package com.demkiv.pet.project.service.security;

import com.demkiv.pet.project.service.entity.security.User;
import com.demkiv.pet.project.service.service.security.CustomService;
import com.demkiv.pet.project.service.util.SecurityServiceException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Optional;

@Configuration
@EnableWebSecurity
@ComponentScan("com.demkiv.pet.project.service.security")
@AllArgsConstructor
public class SecurityConfiguration {

    private CustomService customService;
    private AuthenticationFilter filter;

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
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .formLogin()
                .disable()
                .httpBasic()
                .disable()
                .logout()
                .disable()
                .build();

    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
//            throws Exception {
//        return config.getAuthenticationManager();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Transactional
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(token -> {
            Optional<User> foundUser = customService.findByToken(token);
            if (foundUser.isPresent()) {
                return customService.getUserDetails(foundUser.get());
            } else {
                throw new SecurityServiceException("User is not found");
            }
        });
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}

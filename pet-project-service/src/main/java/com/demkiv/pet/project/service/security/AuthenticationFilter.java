package com.demkiv.pet.project.service.security;

import com.demkiv.pet.project.service.service.security.CustomService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@AllArgsConstructor
@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private CustomService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Optional<String> tokenParam = Optional.ofNullable(request.getHeader(AUTHORIZATION));
        if (tokenParam.isPresent() && SecurityContextHolder.getContext().getAuthentication() == null) {
            String token = StringUtils.removeStart(tokenParam.get(), "Bearer").trim();
            UserDetails foundUser = service.getUserDetails(token);
            if (foundUser != null) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(foundUser, null,
                        service.getAuthorities(tokenParam.get()));
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request, response);
    }
}

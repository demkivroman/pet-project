package com.demkiv.pet.project.service.util;

import org.springframework.security.core.AuthenticationException;

public class SecurityServiceException extends AuthenticationException {
    public SecurityServiceException(String msg) {
        super(msg);
    }

}

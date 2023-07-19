package com.demkiv.pet.project.service.util;

import org.springframework.security.core.AuthenticationException;

public class PetProjectServiceException extends AuthenticationException {
    public PetProjectServiceException(String msg) {
        super(msg);
    }

}

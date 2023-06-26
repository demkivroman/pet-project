package com.demkiv.pet.project.service.controller;

import com.demkiv.pet.project.service.service.CustomService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TokenController {
    private CustomService service;

    @PostMapping("/token")
    public String getToken(@RequestParam("username") final String username,
                           @RequestParam("password") final String password) {
        String token = service.login(username, password);
        if (StringUtils.isEmpty(token)) {
            return "no token found";
        }
        return token;
    }
}

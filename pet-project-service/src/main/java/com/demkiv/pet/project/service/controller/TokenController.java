package com.demkiv.pet.project.service.controller;

import com.demkiv.pet.project.service.service.CustomService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class TokenController {
    private CustomService service;

    @PostMapping("/token")
    public String getToken(@RequestParam("username") final String username,
                           @RequestParam("password") final String password) {
        log.debug("===== FROM TokenController ==========");
        String token = service.login(username, password);
        if (StringUtils.isEmpty(token)) {
            return "no token found";
        }
        return token;
    }
}

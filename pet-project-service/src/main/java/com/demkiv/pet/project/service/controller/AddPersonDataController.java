package com.demkiv.pet.project.service.controller;

import com.demkiv.pet.project.api.model.PersonModel;
import com.demkiv.pet.project.service.service.PetProjectTaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class AddPersonDataController {
    private PetProjectTaskService service;

    @PostMapping(value = "api/add/person",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> processPersonData(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                             @RequestBody PersonModel personData) {
        log.debug("From Controller token" + token);
        return service.getConvertedPersonDataFromApi(personData);
    }
}

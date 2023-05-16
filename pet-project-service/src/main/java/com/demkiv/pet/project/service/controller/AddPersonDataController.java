package com.demkiv.pet.project.service.controller;

import com.demkiv.pet.project.api.model.PersonModel;
import com.demkiv.pet.project.service.service.PetProjectTaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AddPersonDataController {
    private PetProjectTaskService service;

    @PostMapping(value = "/add/person",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> processPersonData(@RequestBody PersonModel personData) {
        return service.getConvertedPersonDataFromApi(personData);
    }
}

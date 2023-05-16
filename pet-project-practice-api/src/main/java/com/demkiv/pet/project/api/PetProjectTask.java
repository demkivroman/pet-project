package com.demkiv.pet.project.api;

import com.demkiv.pet.project.api.model.PersonModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public interface PetProjectTask {
    @GetMapping(value = "/api/ping", produces = MediaType.TEXT_PLAIN_VALUE)
    String ping();

    @PostMapping(value = "/api/add/person",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> processPersonData(@RequestBody PersonModel personData);
}

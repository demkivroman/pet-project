package com.demkiv.pet.project.app;


import com.demkiv.pet.project.api.PetProjectTask;
import com.demkiv.pet.project.api.model.PersonModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@Slf4j
public class PetProjectTaskImpl implements PetProjectTask {
    @Override
    public String ping() {
        return "pong";
    }

    @Override
    public ResponseEntity<String> processPersonData(PersonModel personData) {
        log.debug("On start method");
        log.debug(personData.toString());
        String responseString = "api_name: " +
                personData.getName() + "\n" +
                "api_firstname: " +
                personData.getFirstname() + "\n" +
                "api_age: " +
                personData.getAge() + "\n" +
                "api_position: " +
                personData.getPosition() + "\n" +
                "api_salary: " +
                personData.getSalary();

        Optional<String> result = Optional.of(responseString);
        log.debug("End of the method");
        log.debug(result.get());
        return ResponseEntity.of(result);
    }
}

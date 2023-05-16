package com.demkiv.pet.project.service.service;

import com.demkiv.pet.project.api.model.PersonModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service(value = "petProjectTaskService")
public class PetProjectTaskService {
    private final String PING_PATH = "api/ping";
    private final String ADD_PERSON_PATH = "api/add/person";
    private final String HOST_URL = "http://localhost:8082/";
    private WebClient webClient = WebClient.create(HOST_URL);

    public boolean healthCheckApi() {
        String response = webClient.get().uri(PING_PATH)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return "pong".equals(response);
    }

    public ResponseEntity<String> getConvertedPersonDataFromApi(PersonModel personData) {
        return webClient.post().uri(ADD_PERSON_PATH)
                .body(BodyInserters.fromValue(personData))
                .retrieve()
                .toEntity(String.class)
                .block();
    }
}

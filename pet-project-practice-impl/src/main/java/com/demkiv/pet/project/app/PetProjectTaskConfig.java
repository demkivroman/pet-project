package com.demkiv.pet.project.app;

import com.demkiv.pet.project.api.PetProjectTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class PetProjectTaskConfig {
    @Bean
    public PetProjectTask getPetProjectTaskBean() {
        return new PetProjectTaskImpl();
    }
}

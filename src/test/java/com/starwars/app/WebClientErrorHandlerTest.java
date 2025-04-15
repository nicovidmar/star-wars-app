package com.starwars.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.starwars.exception.CustomizableException;
import com.starwars.service.StarWarsApiService;

import reactor.test.StepVerifier;

@SpringBootTest
public class WebClientErrorHandlerTest {

        @Autowired
        private StarWarsApiService service;

        @Test
        void testGetVehicleById_NotFound() {
                //el vehículo con ID 1 no existe
                StepVerifier.create(service.getVehicleById("1"))
                                .expectErrorMatches(throwable -> throwable instanceof CustomizableException &&
                                                throwable.getMessage().contains("404") &&
                                                throwable.getMessage().contains("Not found"))
                                .verify();
        }
}

package com.learnreactiveprogramming.service;

import lombok.var;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {
        // given

        // when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux();
        // then
        StepVerifier.create(namesFlux)
                //.expectNext("Alex", "Ben", "Chloe")
                //.expectNextCount(3)
                .expectNext("Alex")
                .expectNextCount(2)  
                .verifyComplete();
    }

}
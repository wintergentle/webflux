package com.learnreactiveprogramming.service;

import lombok.var;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

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

    @Test
    void  namesFluxMap() {
        // given
        int stringLength = 3;
        // when
        var namesFlux = fluxAndMonoGeneratorService.namesFluxMap(stringLength);
        // then
        StepVerifier.create(namesFlux)
//                .expectNext("ALEX","BEN","CHLOE")
                .expectNext("ALEX","CHLOE")
//                .expectNext("4-ALEX", "5-CHLOE")
                .verifyComplete();
    }

    @Test
    void  namesFluxImmutability() {
        // given

        // when
        var namesFlux = fluxAndMonoGeneratorService.namesFluxImmutability();

        // then
        StepVerifier.create(namesFlux)
                .expectNext("Alex", "Ben", "Chloe")
                .verifyComplete();
    }

    @Test
    void  namesFluxFlatMap() {
        // given

        // when
        var namesFlux = fluxAndMonoGeneratorService.namesFluxFlatMap(3);
        // then
        StepVerifier.create(namesFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
                .verifyComplete();
    }

    @Test
    void  namesFluxFlatMapAsy() {
        // given

        // when
        var namesFlux = fluxAndMonoGeneratorService.namesFluxFlatMapAsync(3);
        // then
        StepVerifier.create(namesFlux)
//                .expectNext("A","L","E","X","C","H","L","O","E")
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void  namesFluxConcatmap() {
        // given

        // when
        var namesFlux = fluxAndMonoGeneratorService.namesFluxConcatmap(3);
        // then
        StepVerifier.create(namesFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
//                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void nameMonoFlatMap() {
        // given
        int stringLength = 3;
        // when
        var value = fluxAndMonoGeneratorService.nameMonoFlatMap(stringLength);
        // then
        StepVerifier.create(value)
                .expectNext(List.of("A","L","E","X"))
                .verifyComplete();

    }

    @Test
    void nameMonoFlatMapMany() {
        // given
        int stringLength = 3;
        // when
        var value = fluxAndMonoGeneratorService.nameMonoFlatMapMany(stringLength);
        // then
        StepVerifier.create(value)
                .expectNext("A","L","E","X")
                .verifyComplete();

    }

    @Test
    void namesFluxTransform() {
        // given
        int stringLength = 3;
        // when
        var namesFlux = fluxAndMonoGeneratorService.namesFluxTransform(stringLength);
        // then
        StepVerifier.create(namesFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
                .verifyComplete();

    }

    @Test
    void namesFluxTransform_1() {
        // given
        int stringLength = 6;
        // when
        var namesFlux = fluxAndMonoGeneratorService.namesFluxTransform(stringLength);
        // then
        StepVerifier.create(namesFlux)
//                .expectNext("A","L","E","X","C","H","L","O","E")
                .expectNext("default")
                .verifyComplete();

    }

    @Test
    void namesFluxTransformSwitchifEmpty() {
        // given
        int stringLength = 6;
        // when
        var namesFlux = fluxAndMonoGeneratorService.namesFluxTransformSwitchifEmpty(stringLength);
        // then
        StepVerifier.create(namesFlux)
//                .expectNext("A","L","E","X","C","H","L","O","E")
                .expectNext("D","E","F","A","U","L","T")
                .verifyComplete();

    }

    @Test
    void exploreConcat() {
        // given

        // when
        var concatFlux = fluxAndMonoGeneratorService.exploreConcat();
        // then
        StepVerifier.create(concatFlux)
                .expectNext("A", "B", "C","D", "E", "F")
                .verifyComplete();

    }

    @Test
    void exploreConcatWith() {
        // given

        // when
        var concatWithFlux = fluxAndMonoGeneratorService.exploreConcatWith();
        // then
        StepVerifier.create(concatWithFlux)
                .expectNext("A", "B", "C","D", "E", "F")
                .verifyComplete();

    }

    @Test
    void exploreConcatWithMono() {
        // given

        // when
        var concatWithFlux = fluxAndMonoGeneratorService.exploreConcatWithMono();
        // then
        StepVerifier.create(concatWithFlux)
                .expectNext("A", "B")
                .verifyComplete();

    }

    @Test
    void exploreMerge() {
        // given

        // when
        var mergeFlux = fluxAndMonoGeneratorService.exploreMerge();
        // then
        StepVerifier.create(mergeFlux)
                .expectNext("A", "D", "B","E", "C", "F")
                .verifyComplete();

    }

    @Test
    void exploreMergeSequential() {
        // given

        // when
        var mergeFlux = fluxAndMonoGeneratorService.exploreMergeSequential();
        // then
        StepVerifier.create(mergeFlux)
                .expectNext("A", "B", "C","D", "E", "F")
                .verifyComplete();

    }

    @Test
    void exploreZip() {
        // given

        // when
        var zipFlux = fluxAndMonoGeneratorService.exploreZip();
        // then
        StepVerifier.create(zipFlux)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();

    }

    @Test
    void exploreZip1() {
        // given

        // when
        var zipFlux = fluxAndMonoGeneratorService.exploreZip1();
        // then
        StepVerifier.create(zipFlux)
                .expectNext("AD14", "BE25", "CF36")
                .verifyComplete();

    }

}
package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

import lombok.var;


public class FluxAndMonoGeneratorService {

    // create Flux
    protected Flux<String> namesFlux() {
        var namesList = List.of("Alex", "Ben", "Chloe");
        return Flux.fromIterable(namesList).log(); // db or a remote service call
    }

    // create Mono
    protected Mono<String> nameMono() {
        return  Mono.just("Richard").log();
    }



    public static void main(String[] args) {

        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

        fluxAndMonoGeneratorService.namesFlux().subscribe(
                name -> {
                    System.out.println("Name is : " + name);
                }
        );

        fluxAndMonoGeneratorService.nameMono().subscribe(
                name ->{
                    System.out.println("Mono name is : " + name);
                }
        );

    }
}

package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Function;

import lombok.var;
import reactor.util.function.Tuple4;


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

    // create Flux Map
    protected Flux<String> namesFluxMap(int stringLength) {
        // filter the string whose length is greater than 3
        var namesList = List.of("Alex", "Ben", "Chloe");
        return Flux.fromIterable(namesList)
                .map(String::toUpperCase)
//                .map(s -> s.toUpperCase())
                .filter(s -> s.length() > stringLength)
//                .map(s -> s.length() + "-" + s) //4-ALEX, 5-CHLOE
                .log(); // db or a remote service call
    }

    // create Flux Immutability
    protected Flux<String> namesFluxImmutability() {
        var namesFlux = Flux.fromIterable(List.of("Alex", "Ben", "Chloe"));
        // return Flux
        namesFlux.map(String::toUpperCase);
        return  namesFlux;
    }

    protected Mono<String> nameMonoMapFilter(int stringLength) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength);
    }

    protected Mono<List<String>> nameMonoFlatMap(int stringLength) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(this:: splitStringMono)
                .log(); //Mono<List of A, L, E, X>
    }

    protected Flux<String> nameMonoFlatMapMany(int stringLength) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                // transform Mono to Flux
                .flatMapMany(this:: splitString)
                .log(); //Mono<List of A, L, E, X>
    }

    private  Mono<List<String>> splitStringMono(String s) {
        var charArray = s.split("");
        var charList = List.of(charArray); //ALEX -> A, L, E, X
        return Mono.just(charList);
    }

    protected Flux<String> namesFluxFlatMap(int stringLength) {
        // filter the string whose length is greater than 3
        var namesList = List.of("Alex", "Ben", "Chloe");
        return Flux.fromIterable(namesList)
                .map(String::toUpperCase)
//                .map(s -> s.toUpperCase())
                .filter(s -> s.length() > stringLength)
                // ALEX,CHLOE -> A, L, E, X, C, H, L, O, E
                .flatMap(s -> splitString(s)) //A,L,E,X,C,H,L,O,E
                .log(); // db or a remote service call
    }

    protected Flux<String> namesFluxFlatMapAsync(int stringLength) {
        // filter the string whose length is greater than 3
        var namesList = List.of("Alex", "Ben", "Chloe");
        return Flux.fromIterable(namesList)
                .map(String::toUpperCase)
//                .map(s -> s.toUpperCase())
                .filter(s -> s.length() > stringLength)
                // ALEX,CHLOE -> A, L, E, X, C, H, L, O, E
                .flatMap(s -> splitStringWithDelay(s)) //A,L,E,X,C,H,L,O,E
                .log(); // db or a remote service call
    }

    // if ordering matters
    protected Flux<String> namesFluxConcatmap(int stringLength) {
        // filter the string whose length is greater than 3
        var namesList = List.of("Alex", "Ben", "Chloe");
        return Flux.fromIterable(namesList)
                .map(String::toUpperCase)
//                .map(s -> s.toUpperCase())
                .filter(s -> s.length() > stringLength)
                // ALEX,CHLOE -> A, L, E, X, C, H, L, O, E
                .concatMap(s -> splitStringWithDelay(s)) //A,L,E,X,C,H,L,O,E
                .log(); // db or a remote service call
    }

    protected Flux<String> namesFluxTransform(int stringLength) {
        // filter the string whose length is greater than 3
        var namesList = List.of("Alex", "Ben", "Chloe");
        Function<Flux<String>,Flux<String>> filermap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > stringLength);
//        Flux.empty()
        return Flux.fromIterable(namesList)
                .transform(filermap)
                .flatMap(s -> splitString(s)) //A,L,E,X,C,H,L,O,E
                .defaultIfEmpty("default")
                .log(); // db or a remote service call
    }

    protected Flux<String> namesFluxTransformSwitchifEmpty(int stringLength) {
        // filter the string whose length is greater than 3
        var namesList = List.of("Alex", "Ben", "Chloe");
        Function<Flux<String>,Flux<String>> filermap = name ->
                name.map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(s -> splitString(s));
        var defaultFlux =Flux.just("default")
                .transform(filermap);// "D","E","F","A","U","L","T"
//        Flux.empty()
        return Flux.fromIterable(namesList)
                .transform(filermap)
                 //A,L,E,X,C,H,L,O,E
                .switchIfEmpty(defaultFlux)
                .log(); // db or a remote service call
    }

    public  Flux<String> exploreConcat() {

        var abcFlux = Flux.just("A", "B", "C");

        var defFlux = Flux.just("D", "E", "F");

        return Flux.concat(abcFlux, defFlux).log();
    }

    public  Flux<String> exploreConcatWith() {

        var abcFlux = Flux.just("A", "B", "C");

        var defFlux = Flux.just("D", "E", "F");

        return abcFlux.concatWith(defFlux).log();
    }

    public  Flux<String> exploreConcatWithMono() {

        var aMono = Mono.just("A");

        var bMono = Flux.just("B");

        return aMono.concatWith(bMono).log();// A,B
    }

    public  Flux<String> exploreMerge() {

        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));

        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(120));

        return Flux.merge(abcFlux, defFlux).log();
    }

    public  Flux<String> exploreMergeWith() {

        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));

        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(120));

        return abcFlux.mergeWith(defFlux).log();
    }

    public  Flux<String> exploreMergeWithMono() {

        var aMono = Mono.just("A")
                .delayElement(Duration.ofMillis(100));

        var bMono = Flux.just("B")
                .delayElements(Duration.ofMillis(120));

        return aMono.mergeWith(bMono).log();
    }

    public  Flux<String> exploreMergeSequential() {

        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));

        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(120));

        return Flux.mergeSequential(abcFlux, defFlux).log();
    }

    public  Flux<String> exploreZip() {

        var abcFlux = Flux.just("A", "B", "C");

        var defFlux = Flux.just("D", "E", "F");

        return Flux.zip(abcFlux, defFlux, (first, second) -> first + second).log(); //AD, BE, CF
    }

    public  Flux<String> exploreZip1() {

        var abcFlux = Flux.just("A", "B", "C");

        var defFlux = Flux.just("D", "E", "F");

        var _123Flux = Flux.just("1", "2", "3");

        var _456Flux = Flux.just("4", "5", "6");

        return Flux.zip(abcFlux, defFlux, _123Flux, _456Flux)
                .map(t4 -> t4.getT1() + t4.getT2() + t4.getT3() + t4.getT4())
                .log(); //AD14, BE25, CF36
    }

    public  Flux<String> exploreZipWith() {

        var abcFlux = Flux.just("A", "B", "C");

        var defFlux = Flux.just("D", "E", "F");

        return abcFlux.zipWith(defFlux, (first, second) -> first + second).log(); //AD, BE, CF
    }

    public  Mono<String> exploreZipWithMono() {

        var aMono = Mono.just("A");

        var bMono = Mono.just("B");

        return aMono.zipWith(bMono)
                .map(t2 -> t2.getT1() + t2.getT2())//AB
                .log();
    }

    // ALEX -> Flux(A,L,E,X)
    private Flux<String> splitString(String name) {
        var charArray = name.split("");
        return Flux.fromArray(charArray);
    }

    private Flux<String> splitStringWithDelay(String name) {
        var charArray = name.split("");
//        var delay = new Random().nextInt(1000);
        var delay = 1000;
        return Flux.fromArray(charArray)
                .delayElements(Duration.ofMillis(delay));
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

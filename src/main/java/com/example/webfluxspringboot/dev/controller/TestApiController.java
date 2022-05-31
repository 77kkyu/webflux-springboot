package com.example.webfluxspringboot.dev.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@RestController
public class TestApiController {

    @GetMapping("/hi")
    Flux<String> hello() {
        return Flux.just("Hello", "World");
    }

    @GetMapping("/test")
    Flux<String> testGetApi() {
        log.info("test!");
        return Flux.just("T","T");
    }

    @GetMapping("/streamTest")
    Flux<Map<String, Integer>> stream() {
        Stream<Integer> stream= Stream.iterate(0, i -> i + 1);
        return Flux.fromStream(stream.limit(10))
                .map(i -> Collections.singletonMap("value", i));
    }

    @PostMapping("/postMonoTest")
    Mono<String> postTest(@RequestBody Mono<String> body) {
        return body.map(String::toUpperCase);
    }

    @PostMapping("/postFluxTest")
    Flux<Map<String, String>> postSampleFlux(@RequestBody Mono<String> body) {
        Stream<Integer> stream = Stream.iterate(0, i -> i + 1);
        return Flux.fromStream(stream.limit(5))
                .map(i -> Collections.singletonMap("value", body.map(String::toString) + "-" + i.toString()));
    }

}

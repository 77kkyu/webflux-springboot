package com.example.webfluxspringboot.basic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
public class FluxAndMonoTest {

    @Test
    public void Flux_Mono_Test() {
        Flux<Integer> ints = Flux.range(1, 3); // 0~N
        ints.subscribe(System.out::println);

        Mono<String> mono = Mono.just("hello"); // 0~1
        mono.subscribe(System.out::println);
    }

}

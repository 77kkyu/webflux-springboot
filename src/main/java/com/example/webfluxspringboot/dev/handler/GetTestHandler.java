package com.example.webfluxspringboot.dev.handler;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.web.reactive.function.BodyExtractors.toFlux;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class GetTestHandler {

    public Mono<ServerResponse> hello(ServerRequest req) {
        return ok().body(Flux.just("Hello", "World!"), String.class);
    }

    public Mono<ServerResponse> stream(ServerRequest req) {
        Stream<Integer> stream = Stream.iterate(0, i -> i + 1);
        Flux<Map<String, Integer>> flux = Flux.fromStream(stream.limit(10))
                .map(i -> Collections.singletonMap("value", i));
        return ok().contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(flux, new ParameterizedTypeReference<Map<String, Integer>>(){}));
    }

    public Mono<ServerResponse> echo(ServerRequest req) {
        Mono<String> body = req.bodyToMono(String.class).map(String::toUpperCase);
        return ok().body(body,String.class);
    }

    public Mono <ServerResponse> postSampleFlux(ServerRequest req) {
        Flux<Map<String, Integer>> body = req.body(toFlux(
                new ParameterizedTypeReference<Map<String, Integer>>(){}));

        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(fromPublisher(body.map(m->Collections.singletonMap("double", m.get("value") * 2))
                        , new ParameterizedTypeReference<Map<String, Integer>>(){})
                );
    }

}

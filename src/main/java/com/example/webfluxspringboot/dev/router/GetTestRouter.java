package com.example.webfluxspringboot.dev.router;

import com.example.webfluxspringboot.dev.configuration.GreetingHandler;
import com.example.webfluxspringboot.dev.handler.GetTestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * PostTestRouter에서 정리
 */
@Configuration
public class GetTestRouter {

//    @Bean
//    RouterFunction<ServerResponse> simpleRoutes() {
//        return route(GET("/"), request ->
//                ok().body(Flux.just("Hello", "World!"), String.class));
//    }
//
//    @Bean
//    RouterFunction<ServerResponse> routes(GetTestHandler getTestHandler) {
//        return route(GET("/"), getTestHandler::hello)
//                .andRoute(GET("/stream"), getTestHandler::stream);
//    }

}

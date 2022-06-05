package com.example.webfluxspringboot.dev.router;

import com.example.webfluxspringboot.dev.handler.GetTestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class PostTestRouter {

    @Bean
    RouterFunction<ServerResponse> routes(GetTestHandler getTestHandler) {
        return route(GET("/"), getTestHandler::hello)
                .andRoute(GET("/stream"), getTestHandler::stream)
                .andRoute(POST("/echo"), getTestHandler::echo)
                .andRoute(POST("/postSample"), getTestHandler::postSampleFlux);
    }

}

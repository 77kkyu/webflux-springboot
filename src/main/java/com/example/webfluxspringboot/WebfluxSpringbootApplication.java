package com.example.webfluxspringboot;

import com.example.webfluxspringboot.dev.webClient.GreetingWebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Slf4j
@SpringBootApplication
public class WebfluxSpringbootApplication {

    public static void main(String[] args) {

        SpringApplication.run(WebfluxSpringbootApplication.class, args);

        GreetingWebClient gwc = new GreetingWebClient();
        log.info(gwc.getResult());

//        @Bean
//        RouterFunction<ServerResponse> routes() {
//            return RouterFunctions.route(RequestPredicates.GET("/"), request -> ServerResponse.ok().body(Flux.just("Hello", "World!"), String.class));
//        }

    }

}

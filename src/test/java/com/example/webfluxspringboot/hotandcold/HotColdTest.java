package com.example.webfluxspringboot.hotandcold;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@SpringBootTest
public class HotColdTest {

    /**
     * 구독을 하지 않아도 getUser() 메서드 호출
     * 구독을 3번 했지만 hot 변수는 한번 찍힘
     */
    @Test
    public void hot테스트() throws InterruptedException {
        Mono<String> mono = Mono.just(getUser());
        mono.subscribe(System.out::println);
        mono.subscribe(System.out::println);
        mono.subscribe(System.out::println);
    }

    public String getUser() throws InterruptedException {
        String hot = "hot!!";
        System.out.println(hot);
        Thread.sleep(3000);
        return "TTTTTTT!";
    }

    /**
     * 구독하지 않으면 동작하지 않음
     * 독립된 데이터를 생성
     */
    @Test
    public void mono_create_cold() {
        Mono<String> mono = Mono.create(monoSink -> {
            try {
                monoSink.success(getUser());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        mono.subscribe(System.out::println);
        mono.subscribe(System.out::println);
        mono.subscribe(System.out::println);
    }

    /**
     * cold -> hot으로 변경
     */
    @Test
    public void mono_create_cache_hot() {
        Mono<String> mono = Mono.create(monoSkin -> {
            try {
                monoSkin.success(getUser());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        mono = mono.cache(Duration.ofSeconds(3));
        mono.subscribe(System.out::println);
        mono.subscribe(System.out::println);
        mono.subscribe(System.out::println);
    }

    /**
     * cold -> hot
     * publish() 메서드 호출하면 hot, ConnectableFlux으로 변환
     * connect()를 하지 않으면 구독하지 않음
     */
    @Test
    public void connect() {
        Flux<Integer> cold = Flux.range(1,3)
                .doOnSubscribe(x -> System.out.println("subscribe to cold"));

        ConnectableFlux<Integer> hot = cold.publish();
        hot.subscribe(System.out::println);
        hot.subscribe(System.out::println);

        System.out.println("Subscribe 진행중");
        System.out.println("!!");

        hot.connect();
    }

}

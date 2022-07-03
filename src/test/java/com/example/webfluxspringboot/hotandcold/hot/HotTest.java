package com.example.webfluxspringboot.hotandcold.hot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.stream.Stream;

@SpringBootTest
public class HotTest {

    /**
     * 1초 늦게 시작해서 동팔이는 영화 하나를 못봄
     */
    @Test
    public void cinema() throws InterruptedException {
        final Flux<String> hotFlux = Flux.fromStream(this::getMovies)
                .doOnSubscribe(x -> System.out.println("스트리밍 시작!"))
                .delayElements(Duration.ofSeconds(1))
                .share();

        hotFlux.publishOn(Schedulers.newBoundedElastic(1, 1, "철규"))
                .subscribe(scene -> System.out.println("철규가 보는 영화 " + scene));
        Thread.sleep(1100L);

        hotFlux.publishOn(Schedulers.newBoundedElastic(1, 1, "동팔"))
                .subscribe(scene -> System.out.println("동팔이 보는 영화 " + scene));
        Thread.sleep(5000L);
    }

    private Stream<String> getMovies() {
        return Stream.of("범죄도시2", "타짜", "독전", "아이언맨");
    }

}

package com.example.webfluxspringboot.hotandcold.cold;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.stream.Stream;

@SpringBootTest
public class ColdTest {

    /**
     *  1초 늦게 시작해도 각자 독립적으로 실행
     */
    @Test
    public void netflix() throws InterruptedException {
        final Flux<String> hotFlux = Flux.fromStream(this::getMovies)
                .doOnSubscribe(x -> System.out.println("스트리밍 시작!"))
                .delayElements(Duration.ofSeconds(1));

        hotFlux.publishOn(Schedulers.newBoundedElastic(1, 1, "철규"))
                .subscribe(scene -> System.out.println("철규가 보는 영화 " + scene));
        Thread.sleep(1000L);

        hotFlux.publishOn(Schedulers.newBoundedElastic(1, 1, "동팔"))
                .subscribe(scene -> System.out.println("동팔이 보는 영화 " + scene));
        Thread.sleep(5000L);
    }

    private Stream<String> getMovies() {
        return Stream.of("범죄도시2", "타짜", "독전", "아이언맨");
    }

}

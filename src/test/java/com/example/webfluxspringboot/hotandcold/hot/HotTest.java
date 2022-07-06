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

    /**
     * 만약 철규가 모든 영화를 다 보고 동팔이가 영화관에 도착하면 처음부터 다시 보여줌
     */
    @Test
    public void cinema2() throws InterruptedException {
        final Flux<String> hotFlux = Flux.fromStream(this::getMovies)
                .doOnSubscribe(x -> System.out.println("스트리밍 시작!"))
                .delayElements(Duration.ofSeconds(1))
                .share();

        hotFlux.publishOn(Schedulers.newBoundedElastic(1, 1, "철규"))
                .subscribe(scene -> System.out.println("철규가 보는 영화 " + scene));
        Thread.sleep(6000L);

        hotFlux.publishOn(Schedulers.newBoundedElastic(1, 1, "동팔"))
                .subscribe(scene -> System.out.println("동팔이 보는 영화 " + scene));
        Thread.sleep(5000L);
    }

    /**
     *  publish().autoConnect()를 사용하면 모든 영화가 끝난 이후에 도착하면 동작 안함
     */
    @Test
    public void cinema_autoConnect() throws InterruptedException {
        final Flux<String> hotFlux = Flux.fromStream(this::getMovies)
                .doOnSubscribe(x -> System.out.println("스트리밍 시작!"))
                .delayElements(Duration.ofSeconds(1))
                .publish()
                .autoConnect();

        hotFlux.publishOn(Schedulers.newBoundedElastic(1, 1, "철규"))
                .subscribe(scene -> System.out.println("철규가 보는 영화 " + scene));
        Thread.sleep(6000L);

        hotFlux.publishOn(Schedulers.newBoundedElastic(1, 1, "동팔"))
                .subscribe(scene -> System.out.println("동팔이 보는 영화 " + scene));
        Thread.sleep(5000L);
    }

    /**
     * cache()를 사용하면 늦께 와도 마치 녹화한 영상을 보여주는 것과 똑같은 원리
     * 스트리밍은 한 번 했지만 계속 볼 수 있다
     */
    @Test
    public void cinema_cache() throws InterruptedException {
        final Flux<String> hotFlux = Flux.fromStream(this::getMovies)
                .doOnSubscribe(x -> System.out.println("스트리밍 시작!"))
                .delayElements(Duration.ofSeconds(1))
                .cache();

        hotFlux.publishOn(Schedulers.newBoundedElastic(1, 1, "철규"))
                .subscribe(scene -> System.out.println("철규가 보는 영화 " + scene));
        Thread.sleep(6000L);

        hotFlux.publishOn(Schedulers.newBoundedElastic(1, 1, "동팔"))
                .subscribe(scene -> System.out.println("동팔이 보는 영화 " + scene));
        Thread.sleep(5000L);
    }

    private Stream<String> getMovies() {
        return Stream.of("범죄도시2", "타짜", "독전", "아이언맨");
    }

}

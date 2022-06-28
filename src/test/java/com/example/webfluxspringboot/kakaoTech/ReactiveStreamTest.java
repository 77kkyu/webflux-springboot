package com.example.webfluxspringboot.kakaoTech;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class ReactiveStreamTest {

    final List<String> basket1 = Arrays.asList(new String[]{"kiwi", "orange", "lemon", "orange", "lemon", "kiwi"});
    final List<String> basket2 = Arrays.asList(new String[]{"banana", "lemon", "lemon", "kiwi"});
    final List<String> basket3 = Arrays.asList(new String[]{"strawberry", "orange", "lemon", "grape", "strawberry"});
    final List<List<String>> baskets = Arrays.asList(basket1, basket2, basket3);
    final Flux<List<String>> basketFlux = Flux.fromIterable(baskets);

    /**
     * 블로킹 방식으로 동작 -> 논 블로킹 방식을 전혀 살릴 수 없다
     * 한번에 해결할 수 있는데 2번의 basket을 순회 하므로 비효율
     */
    @Test
    public void mainTest() {
        basketFlux.concatMap(basket -> {
            final Mono<List<String>> distinctFruits = Flux.fromIterable(basket).distinct().collectList();
            final Mono<Map<String, Long>> countFruitsMono = Flux.fromIterable(basket)
                    .groupBy(fruit -> fruit) // 바구니로 부터 넘어온 과일 기준으로 group을 묶는다.
                    .concatMap(groupedFlux -> groupedFlux.count()
                            .map(count -> {
                                final Map<String, Long> fruitCount = new LinkedHashMap<>();
                                fruitCount.put(groupedFlux.key(), count);
                                return fruitCount;
                            }) // 각 과일별로 개수를 Map으로 리턴
                    ) // concatMap으로 순서보장
                    .reduce((accumulatedMap, currentMap) -> new LinkedHashMap<String, Long>() { {
                        putAll(accumulatedMap);
                        putAll(currentMap);
                    }}); // 그동안 누적된 accumulatedMap에 현재 넘어오는 currentMap을 합쳐서 새로운 Map을 만든다. // map끼리 putAll하여 하나의 Map으로 만든다.
            return Flux.zip(distinctFruits, countFruitsMono, (distinct, count) -> new FruitInfo(distinct, count));
        }).subscribe(System.out::println);
    }

    @Test
    public void subscribeOnTest() {
        basketFlux.concatMap(basket -> {
            final Mono<List<String>> distinctFruits = Flux.fromIterable(basket).distinct().collectList().subscribeOn(Schedulers.parallel());
            final Mono<Map<String, Long>> countFruitsMono = Flux.fromIterable(basket)
                    .groupBy(fruit -> fruit) // 바구니로 부터 넘어온 과일 기준으로 group을 묶는다.
                    .concatMap(groupedFlux -> groupedFlux.count()
                            .map(count -> {
                                final Map<String, Long> fruitCount = new LinkedHashMap<>();
                                fruitCount.put(groupedFlux.key(), count);
                                return fruitCount;
                            }) // 각 과일별로 개수를 Map으로 리턴
                    ) // concatMap으로 순서보장
                    .reduce((accumulatedMap, currentMap) -> new LinkedHashMap<String, Long>() { {
                        putAll(accumulatedMap);
                        putAll(currentMap);
                    }}) // 그동안 누적된 accumulatedMap에 현재 넘어오는 currentMap을 합쳐서 새로운 Map을 만든다. // map끼리 putAll하여 하나의 Map으로 만든다.
                    .subscribeOn(Schedulers.parallel());
            return Flux.zip(distinctFruits, countFruitsMono, (distinct, count) -> new FruitInfo(distinct, count));
        }).subscribe(System.out::println);
    }

    @Test
    public void subscribeOnTest2() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        basketFlux.concatMap(basket -> {
            final Mono<List<String>> distinctFruits = Flux.fromIterable(basket).distinct().collectList().subscribeOn(Schedulers.parallel());
            final Mono<Map<String, Long>> countFruitsMono = Flux.fromIterable(basket).log()
                    .groupBy(fruit -> fruit) // 바구니로 부터 넘어온 과일 기준으로 group을 묶는다.
                    .concatMap(groupedFlux -> groupedFlux.count()
                            .map(count -> {
                                final Map<String, Long> fruitCount = new LinkedHashMap<>();
                                fruitCount.put(groupedFlux.key(), count);
                                return fruitCount;
                            }) // 각 과일별로 개수를 Map으로 리턴
                    ) // concatMap으로 순서보장
                    .reduce((accumulatedMap, currentMap) -> new LinkedHashMap<String, Long>() { {
                        putAll(accumulatedMap);
                        putAll(currentMap);
                    }}) // 그동안 누적된 accumulatedMap에 현재 넘어오는 currentMap을 합쳐서 새로운 Map을 만든다. // map끼리 putAll하여 하나의 Map으로 만든다.
                    .subscribeOn(Schedulers.parallel());
            return Flux.zip(distinctFruits, countFruitsMono, (distinct, count) -> new FruitInfo(distinct, count));
        }).subscribe(
                System.out::println,  // 값이 넘어올 때 호출 됨, onNext(T)
                error -> {
                    System.err.println(error);
                    countDownLatch.countDown();
                }, // 에러 발생시 출력하고 countDown, onError(Throwable)
                () -> {
                    System.out.println("complete");
                    countDownLatch.countDown();
                } // 정상적 종료시 countDown, onComplete()
        );
        countDownLatch.await(2, TimeUnit.SECONDS);
    }

    @Test
    public void hotFluxTest() {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        basketFlux.concatMap(basket -> {
            final Flux<String> source = Flux.fromIterable(basket).log().publish().autoConnect(2);
            final Mono<List<String>> distinctFruits = source.distinct().collectList();
            final Mono<Map<String, Long>> countFruitsMono = source
                    .groupBy(fruit -> fruit) // 바구니로 부터 넘어온 과일 기준으로 group을 묶는다.
                    .concatMap(groupedFlux -> groupedFlux.count()
                            .map(count -> {
                                final Map<String, Long> fruitCount = new LinkedHashMap<>();
                                fruitCount.put(groupedFlux.key(), count);
                                return fruitCount;
                            }) // 각 과일별로 개수를 Map으로 리턴
                    ) // concatMap으로 순서보장
                    .reduce((accumulatedMap, currentMap) -> new LinkedHashMap<String, Long>() { {
                        putAll(accumulatedMap);
                        putAll(currentMap);
                    }}); // 그동안 누적된 accumulatedMap에 현재 넘어오는 currentMap을 합쳐서 새로운 Map을 만든다. // map끼리 putAll하여 하나의 Map으로 만든다.
            return Flux.zip(distinctFruits, countFruitsMono, (distinct, count) -> new FruitInfo(distinct, count));
        }).subscribe(
                System.out::println,  // 값이 넘어올 때 호출 됨, onNext(T)
                error -> {
                    System.err.println(error);
                    countDownLatch.countDown();
                }, // 에러 발생시 출력하고 countDown, onError(Throwable)
                () -> {
                    System.out.println("complete");
                    countDownLatch.countDown();
                } // 정상적 종료시 countDown, onComplete()
        );
    }

    @Test
    public void parallelTest() {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        basketFlux.concatMap(basket -> {
            final Flux<String> source = Flux.fromIterable(basket).log().publish().autoConnect(2);
            final Mono<List<String>> distinctFruits = source.distinct().collectList().log().subscribeOn(Schedulers.parallel());
            final Mono<Map<String, Long>> countFruitsMono = source
                    .groupBy(fruit -> fruit) // 바구니로 부터 넘어온 과일 기준으로 group을 묶는다.
                    .concatMap(groupedFlux -> groupedFlux.count()
                            .map(count -> {
                                final Map<String, Long> fruitCount = new LinkedHashMap<>();
                                fruitCount.put(groupedFlux.key(), count);
                                return fruitCount;
                            }) // 각 과일별로 개수를 Map으로 리턴
                    ) // concatMap으로 순서보장
                    .reduce((accumulatedMap, currentMap) -> new LinkedHashMap<String, Long>() { {
                        putAll(accumulatedMap);
                        putAll(currentMap);
                    }}) // 그동안 누적된 accumulatedMap에 현재 넘어오는 currentMap을 합쳐서 새로운 Map을 만든다. // map끼리 putAll하여 하나의 Map으로 만든다.
                    .log()
                    .subscribeOn(Schedulers.parallel());
            return Flux.zip(distinctFruits, countFruitsMono, (distinct, count) -> new FruitInfo(distinct, count));
        }).subscribe(
                System.out::println,  // 값이 넘어올 때 호출 됨, onNext(T)
                error -> {
                    System.err.println(error);
                    countDownLatch.countDown();
                }, // 에러 발생시 출력하고 countDown, onError(Throwable)
                () -> {
                    System.out.println("complete");
                    countDownLatch.countDown();
                } // 정상적 종료시 countDown, onComplete()
        );
    }

    @Test
    public void publishOnTest() {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        basketFlux.concatMap(basket -> {
            final Flux<String> source = Flux.fromIterable(basket).log().publish().autoConnect(2);
            final Mono<List<String>> distinctFruits = source.publishOn(Schedulers.parallel()).distinct().collectList().log();
            final Mono<Map<String, Long>> countFruitsMono = source.publishOn(Schedulers.parallel())
                    .groupBy(fruit -> fruit) // 바구니로 부터 넘어온 과일 기준으로 group을 묶는다.
                    .concatMap(groupedFlux -> groupedFlux.count()
                            .map(count -> {
                                final Map<String, Long> fruitCount = new LinkedHashMap<>();
                                fruitCount.put(groupedFlux.key(), count);
                                return fruitCount;
                            }) // 각 과일별로 개수를 Map으로 리턴
                    ) // concatMap으로 순서보장
                    .reduce((accumulatedMap, currentMap) -> new LinkedHashMap<String, Long>() { {
                        putAll(accumulatedMap);
                        putAll(currentMap);
                    }}) // 그동안 누적된 accumulatedMap에 현재 넘어오는 currentMap을 합쳐서 새로운 Map을 만든다. // map끼리 putAll하여 하나의 Map으로 만든다.
                    .log();
            return Flux.zip(distinctFruits, countFruitsMono, (distinct, count) -> new FruitInfo(distinct, count));
        }).subscribe(
                System.out::println,  // 값이 넘어올 때 호출 됨, onNext(T)
                error -> {
                    System.err.println(error);
                    countDownLatch.countDown();
                }, // 에러 발생시 출력하고 countDown, onError(Throwable)
                () -> {
                    System.out.println("complete");
                    countDownLatch.countDown();
                } // 정상적 종료시 countDown, onComplete()
        );
    }

}

package com.example.webfluxspringboot.basic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@SpringBootTest
public class ReactiveTest {

    @Test
    public void Test() {
        String str = "abcd";
        Mono<String> mono = Mono.just(str);
        mono.subscribe(x -> {
            System.out.println(str);
        });

        Mono<Object> function = Mono.create( x -> {
            //x.error(new Exception("에러발생")); //에러 발생시킴
            x.success("success"); // success값 전달
        });
        function.subscribe(x-> {
            System.out.println(x);
        });

        mono.doOnNext(x-> { //처음 구독한 다음행위에 대한 지정
            System.out.println("next : " + x);
        }).subscribe();

        CompletableFuture<?> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("시작!!");
            return "PARAM";
        });
        Mono<Object> mono2 = Mono.fromFuture(future); // 비동기에 대한 구독
        mono2.subscribe((param) -> {
            System.out.println("param : " + param);
        });

    }

}

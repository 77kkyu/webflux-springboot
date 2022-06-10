package com.example.webfluxspringboot.lambdaExpression;

import com.example.webfluxspringboot.lambdaExpression.lambInterface.LambdaFn;
import com.example.webfluxspringboot.lambdaExpression.lambInterface.LambdaParamFn;
import com.example.webfluxspringboot.lambdaExpression.lambInterface.LambdaStringFn;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootTest
public class LambdaTest {

    private LambdaFn lambdaFn;
    private LambdaParamFn lambdaParamFn;
    private LambdaStringFn lambdaStringFn;

    /**
     * 매개변수가 없고 리턴값이 없는 람다식
     */
    @Test
    public void 람다_테스트() {
        lambdaFn = () -> {
            System.out.println("kkyu kkyu kkyu kkyu");
        };
        lambdaFn.newCoding();

        // 실행코드가 1줄이면 {} 생략가능
        lambdaFn = () -> System.out.println("kkyu kkyu kkyu kkyu");
        lambdaFn.newCoding();
    }

    /**
     * 매개변수가 있고 리턴값이 없는 람다식
     */
    @Test
    public void 람다_파라미터_테스트() {
        String param;
        lambdaParamFn = (a) -> {
            System.out.println(a + "Best!!");
        };
        param = "안녕하세용";
        lambdaParamFn.paramFn(param);

        // 매개변수가 1개이면 ()생략 가능
        lambdaParamFn = a -> System.out.println(a + "happy!!");
        param = "누구십니까";
        lambdaParamFn.paramFn(param);
    }

    /**
     * 매개변수가 없고 리턴값이 있는 람다식
     */
    @Test
    public void 람다_리턴_테스트() {
        String str1 = "안녕하세요";
        String str2 = "누구입니까";

        lambdaStringFn = () -> {
            return str1;
        };
        System.out.println(lambdaStringFn.strFn());

        // 실행코드가 return만 있으면 {}와 return문 생략가능
        lambdaStringFn = () -> str2;
        System.out.println(lambdaStringFn.strFn());
    }

    /**
     *  Supplier는 매개변수 없이 반환값 만을 갖는 함수형 인터페이스입니다
     *  Supplier는 T get()을 추상 메소드로 갖고 있습니다
     */
    @Test
    public void supplierTest() {
        Supplier<String> supplier = () -> "77kkyu hi";
        System.out.println("supplier : " + supplier.get());
    }

    /**
     *   Consumer는 객체 T을 매개변수로 받아서 사용, 반환값이 없는 함수형 인터페이스입니다
     *   Consumer는 void accept(T t)를 추상 메소드로 가지고 있습니다
     *   andThen이라는 함수를 제공하고 이를 통해 하나의 함수가 끝난 후 다음 Consumer를 계속해서 이용할 수 있습니다
     *   원본의 데이터는 유지된다
    */
    @Test
    public void consumerTest() {
        Consumer<String> consumer = (str) -> System.out.println(str.split(" ")[0]);
        consumer.andThen(System.out::println).accept("77kkyu hi");
    }

    /**
     *  Function은 객체 T을 받아서 R로 반환하는 함수형 인터페이스입니다
     *  Function은 R apply(T t)를 추상 메서드로 가지고 있습니다
     *  andThen 함수를 제공하고, compose 함수도 제공합니다
     *  compose는 첫 번째 함수 실행 이전에 먼저 함수를 실행하여 연쇄적으로 연결시켜준다
     *  identity 함수는 자기 자신을 반환하는 static 함수입니다
     */
    @Test
    public void functionTest() {
        Function<String, Integer> function = str -> str.length();
        System.out.println("apply : " + function.apply("77kkyu hi"));
    }

}

package com.example.webfluxspringboot.lambdaExpression;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.*;

@SpringBootTest
public class SupplierTest {

    /**
     *  Supplier는 매개변수 없이 반환값 만을 갖는 함수형 인터페이스입니다
     *  Supplier는 T get()을 추상 메소드로 갖고 있습니다
     */
    String string = "dev world";
    boolean aBoolean = true;
    double aDouble = 123.45;
    int anInt = 456;
    long aLong = 12345567;

    @Test
    public void supplierTest() {
        Supplier<String> supplier = () -> string;
        BooleanSupplier booleanSupplier = () -> aBoolean;
        DoubleSupplier doubleSupplier = () -> aDouble;
        IntSupplier intSupplier = () -> anInt;
        LongSupplier longSupplier = () -> aLong;

        String s = supplier.get();
        System.out.println("String 값 : "+ s);

        boolean b = booleanSupplier.getAsBoolean();
        System.out.println("boolean 값 : "+ b);

        double d = doubleSupplier.getAsDouble();
        System.out.println("double 값 : "+ d);

        int i = intSupplier.getAsInt();
        System.out.println("int 값 : "+ i);

        long l = longSupplier.getAsLong();
        System.out.println("long 값 : "+ l);
    }

}

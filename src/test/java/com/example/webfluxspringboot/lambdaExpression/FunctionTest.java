package com.example.webfluxspringboot.lambdaExpression;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.BiFunction;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.ToDoubleBiFunction;

@SpringBootTest
public class FunctionTest {

    /**
     *  Function은 객체 T을 받아서 R로 반환하는 함수형 인터페이스입니다
     *  Function은 R apply(T t)를 추상 메서드로 가지고 있습니다
     *  andThen 함수를 제공하고, compose 함수도 제공합니다
     *  compose는 첫 번째 함수 실행 이전에 먼저 함수를 실행하여 연쇄적으로 연결시켜준다
     *  identity 함수는 자기 자신을 반환하는 static 함수입니다
     */

    class Student {
        private int stuNum;
        private String stuName;
        private int math;
        private int english;
        Student(int stuNum, String stuName, int math, int english) {
            this.stuNum = stuNum;
            this.stuName = stuName;
            this.math = math;
            this.english = english;
        }
    }

    @Test
    public void functionTest() {
        FunctionTest functionTest = new FunctionTest();
        Student st1 = functionTest.new Student(123, "홍길동", 11, 36);

        //매핑 : Student객체 - Student의 Integer 값
        Function<Student, Integer> function = a -> a.stuNum;
        int resultInt = function.apply(st1);
        System.out.println("홍길동 번호 : " + resultInt);

        //매핑 : 두 Integer, Double 값
        BiFunction<Integer, Integer, Double> biFunction = (a, b) -> (double) (a+b)/2;
        double resultDouble = biFunction.apply(3, 5);
        System.out.println("두 숫자 평균 : " + resultDouble);

        //매핑 : Double, Integer 값
        DoubleFunction<Integer> doubleFunction = a -> {
            Double d = Math.floor(a);
            return d.intValue();
        };
        int intValue = doubleFunction.apply(244.77);
        System.out.println("소수점 버리기 : " + intValue);

        //매핑 : Integer, Integer - Double
        ToDoubleBiFunction<Integer, Integer> toDoubleBiFunction;
        toDoubleBiFunction = (math, english) -> (double) (math+english)/2;
        double resultDouble2 = toDoubleBiFunction.applyAsDouble(st1.math, st1.english);
        System.out.println("홍길동의 수학 영어 평균 : " + resultDouble2);
    }

}

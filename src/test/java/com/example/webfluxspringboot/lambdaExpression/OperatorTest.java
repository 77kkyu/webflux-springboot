package com.example.webfluxspringboot.lambdaExpression;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;
import java.util.function.UnaryOperator;

@SpringBootTest
public class OperatorTest {

    int[] numbers = {3, 1, 6, 7, 5};
    double[] celciousArr = {25, 44, 100, 0};

    @Test
    public void operatorTest() {
        OperatorTest operatorTest = new OperatorTest();
        int max = operatorTest.getMax((a, b) -> {
            int number = a;
            if (a < b) {
                number = b;
            }
            return number;
        });
        System.out.println("최댓값 : " + max);

        System.out.print("제곱값 : ");
        int[] intArr = operatorTest.getSquare(a -> a * a);
        for (int d : intArr) {
            System.out.println(d + " ");
        }

        System.out.print("\n섭씨 화씨 바꾸기 : ");
        operatorTest.getSumOfMultiple(a -> a * 9/5 + 32);
    }

    int getMax(IntBinaryOperator intBinaryOperator) {
        int result = numbers[0];
        for (int number : numbers) { // int[] numbers 반복하면서 operator 연산 수행 및 리턴
            result = intBinaryOperator.applyAsInt(result, number);
        }
        return result;
    }

    int[] getSquare(IntUnaryOperator intUnaryOperator) {
        int[] intArr = new int[numbers.length];
        for (int i=0; i<numbers.length; i++) {
            intArr[i] = intUnaryOperator.applyAsInt(numbers[i]);
        }
        return intArr;
    }

    void getSumOfMultiple(UnaryOperator<Double> unaryOperator) {
        int sum = 0;
        for (double celcious : celciousArr) {
            double fahrenheit = unaryOperator.apply(celcious);
            System.out.println(fahrenheit + " ");
        }
    }

}

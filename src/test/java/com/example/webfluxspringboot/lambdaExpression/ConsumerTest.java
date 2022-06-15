package com.example.webfluxspringboot.lambdaExpression;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.*;

@SpringBootTest
public class ConsumerTest {

    /**
     *   Consumer는 객체 T을 매개변수로 받아서 사용, 반환값이 없는 함수형 인터페이스입니다
     *   Consumer는 void accept(T t)를 추상 메소드로 가지고 있습니다
     *   andThen이라는 함수를 제공하고 이를 통해 하나의 함수가 끝난 후 다음 Consumer를 계속해서 이용할 수 있습니다
     *   원본의 데이터는 유지된다
     */
    Consumer<String> c1 = a -> System.out.println("c1 : " + a);
    BiConsumer<String, Integer> c2 = (a, b) -> System.out.println("c2 : " + a + ", " + b);
    IntConsumer c3 = a -> System.out.println("c3 : " + a);
    DoubleConsumer c4 = a -> System.out.println("c4 : " + a);
    LongConsumer c5 = a -> System.out.println("c5 : " + a);
    ObjIntConsumer<Student> c6 = (a, b) -> System.out.println("이름 : " + a.name + ", 나이 : " + b);
    ObjDoubleConsumer<Student> c7 = (a, b) -> System.out.println("이름 : " + a.name + ", 나이 : " + b);
    ObjLongConsumer<Student> c8 = (a, b) -> System.out.println("이름 : " + a.name + ", 나이 : " + b);

    class Student {
        private String name;
        Student(String name){this.name = name;};
    }

    @Test
    public void ConsumerTest() {
        ConsumerTest consumerTest = new ConsumerTest();
        consumerTest.c1.accept("77kkyu");
        consumerTest.c2.accept("77kkyu", 123);
        consumerTest.c3.accept(5555);
        consumerTest.c4.accept(123.45);
        consumerTest.c5.accept(12345667);

        Student student = consumerTest.new Student("77kkyu");
        consumerTest.c6.accept(student, 123);
        consumerTest.c7.accept(student, 456.12);
        consumerTest.c8.accept(student, 789);
    }

}

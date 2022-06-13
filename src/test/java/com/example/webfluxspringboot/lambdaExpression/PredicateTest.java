package com.example.webfluxspringboot.lambdaExpression;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@SpringBootTest
public class PredicateTest {

    private List<Student> list;

    private enum Gender { MALE, FEMALE }

    class Student{
        private String name;
        private Gender gender;
        private int score;

        Student(String name, Gender gender, int score){
            this.name = name;
            this.gender = gender;
            this.score = score;
        }
    }

    @Test
    public void predicateTest() {
        PredicateTest predicateTest = new PredicateTest();
        predicateTest.list = Arrays.asList(
            predicateTest.new Student("1", Gender.MALE, 33),
            predicateTest.new Student("2", Gender.FEMALE, 50),
            predicateTest.new Student("3", Gender.MALE, 92),
            predicateTest.new Student("4", Gender.FEMALE, 45)
        );

        Predicate<Student> predicate_male = t -> t.gender.equals(Gender.MALE);
        double avgOfMale = predicateTest.getAverage(predicate_male);
        System.out.println("남성 평균 점수 : " + avgOfMale);

        Predicate<Student> predicate_sixty = t -> t.score >= 60;
        double avgOver60 = predicateTest.getAverage(predicate_sixty);
        System.out.println("60점 평균 점수 : " + avgOver60);

    }

    private double getAverage(Predicate<Student> predicate){
        int count = 0;
        int sum = 0;
        for (Student stu : list){
            if(predicate.test(stu)){
                count++;
                sum += stu.score;
            }
        }
        return (double) sum/count;
    }

}

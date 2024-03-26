package ru.otus.novikov.java.hw2;

import ru.otus.novikov.java.hw2.annotations.AfterSuite;
import ru.otus.novikov.java.hw2.annotations.BeforeSuite;
import ru.otus.novikov.java.hw2.annotations.Test;

public class TestClass {

    public TestClass() {
    }

    @BeforeSuite
    void beforeAll() {
        System.out.println("BeforeSuite");
    }

    @Test(priority = 10)
    void one() {
        System.out.println("Test one");
    }

    @Test(priority = 1)
    void two() {
        System.out.println("Test two");
    }

    @Test(priority = 6)
    void three() {
        System.out.println("Test three");
    }

    @Test(priority = 3)
    void four() {
        System.out.println("Test four");
        throw new RuntimeException("Ups");
    }

    @AfterSuite
    void afterSuite() {
        System.out.println("AfterSuite");
    }
}

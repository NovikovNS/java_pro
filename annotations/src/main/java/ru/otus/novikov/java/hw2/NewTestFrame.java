package ru.otus.novikov.java.hw2;

import ru.otus.novikov.java.hw2.annotations.AfterSuite;
import ru.otus.novikov.java.hw2.annotations.BeforeSuite;
import ru.otus.novikov.java.hw2.annotations.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class NewTestFrame {

    public static void startTesting(String className) throws Exception {
        try {
            Class<?> clazz = Class.forName(className);
            if(!checkMethodsAndAttributes(clazz)) {
                return;
            }
            testing(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkMethodsAndAttributes(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();

        List<Method> beforeSuiteMethods = Arrays.stream(methods).filter(it -> it.isAnnotationPresent(BeforeSuite.class)).toList();
        if (beforeSuiteMethods.size() > 1) {
            System.out.println("More one @BeforeSuite annotation in testing class");
            return false;
        }

        List<Method> afterSuiteMethods = Arrays.stream(methods).filter(it -> it.isAnnotationPresent(AfterSuite.class)).toList();
        if (afterSuiteMethods.size() > 1) {
            System.out.println("More one @AfterSuite annotation in testing class");
            return false;
        }

        List<Method> testMethods = Arrays.stream(methods)
            .filter(it -> it.isAnnotationPresent(Test.class)).toList();

        if (!testMethods.stream().filter(it ->
                it.getAnnotation(Test.class).priority() > 10 ||
                    it.getAnnotation(Test.class).priority() < 0)
            .toList().isEmpty()) {
            System.out.println("Impossible value for priority of tests");
            return false;
        }

        return true;
    }

    private static void testing(Class<?> clazz) throws Exception {
        int pass = 0;
        int failed = 0;
        Method[] methods = clazz.getDeclaredMethods();
        Object newTest = clazz.getConstructor().newInstance();

        Optional<Method> beforeSuiteMethod = Arrays.stream(methods)
            .filter(it -> it.isAnnotationPresent(BeforeSuite.class))
            .findAny();

        Optional<Method> afterSuiteMethod = Arrays.stream(methods)
            .filter(it -> it.isAnnotationPresent(AfterSuite.class))
            .findAny();

        List<Method> testMethods = Arrays.stream(methods)
            .filter(it -> it.isAnnotationPresent(Test.class)).toList();

        List<Method> sortedTestMethods = testMethods.stream()
            .sorted(Comparator.comparing(it -> it.getAnnotation(Test.class).priority(), Comparator.reverseOrder()))
            .toList();

        if (beforeSuiteMethod.isPresent()) {
            beforeSuiteMethod.get().invoke(newTest);
        }

        for (Method m: sortedTestMethods) {
            try {
                m.invoke(newTest);
                pass++;
            } catch (Throwable ex) {
                failed++;
            }
        }

        if (afterSuiteMethod.isPresent()) {
            afterSuiteMethod.get().invoke(newTest);
        }

        System.out.printf("Testing completed. All tests: %d. Passed tests: %d. Failed tests: %d", testMethods.size(), pass, failed);

    }
}

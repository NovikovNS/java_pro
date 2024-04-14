package ru.otus.novikov.java.hw2;

import ru.otus.novikov.java.hw2.annotations.AfterSuite;
import ru.otus.novikov.java.hw2.annotations.BeforeSuite;
import ru.otus.novikov.java.hw2.annotations.Test;
import ru.otus.novikov.java.hw2.exceptions.AnnotationConstraintException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class NewTestFrame {

    public static void testingClass(String className) throws Exception {
        try {
            Class<?> clazz = Class.forName(className);
            List<Method> preparedTestMethods = preparingTestMethods(clazz);
            if (!preparedTestMethods.isEmpty()) {
                testing(clazz, preparedTestMethods);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static List<Method> preparingTestMethods(Class<?> clazz) {
        Method[] declaredMethods = clazz.getDeclaredMethods();

        List<Method> beforeSuiteMethods = Arrays.stream(declaredMethods)
            .filter(it -> it.isAnnotationPresent(BeforeSuite.class)).toList();

        // Check @BeforeSuite annotation
        if (beforeSuiteMethods.size() > 1) {
            throw new AnnotationConstraintException("More one @BeforeSuite annotation in testing class");
        }

        List<Method> afterSuiteMethods = Arrays.stream(declaredMethods)
            .filter(it -> it.isAnnotationPresent(AfterSuite.class))
            .toList();

        // Check @AfterSuite annotation
        if (afterSuiteMethods.size() > 1) {
            throw new AnnotationConstraintException("More one @AfterSuite annotation in testing class");
        }

        List<Method> testMethods = Arrays.stream(declaredMethods)
            .filter(it -> it.isAnnotationPresent(Test.class)).toList();

        // Check @Test annotation
        if (testMethods.stream().anyMatch(it ->
            it.getAnnotation(Test.class).priority() > 10 ||
                it.getAnnotation(Test.class).priority() < 0)
        ) {
            throw new AnnotationConstraintException("Impossible value for priority of tests");
        }

        List<Method> sortedTestMethods = testMethods.stream()
            .sorted(Comparator.comparing(it -> it.getAnnotation(Test.class).priority(), Comparator.reverseOrder()))
            .toList();

        return Stream.of(beforeSuiteMethods, sortedTestMethods, afterSuiteMethods).flatMap(Collection::stream).toList();
    }

    private static void testing(Class<?> clazz, List<Method> preparedTestMethods) {
        int pass = 0;
        int failed = 0;
        TestClass newTest = new TestClass();

        for (Method m : preparedTestMethods) {
            try {
                m.invoke(newTest);
                pass++;
            } catch (Exception ex) {
                failed++;
            }
        }

        System.out.printf("Testing completed. All tests: %d. Passed tests: %d. Failed tests: %d", preparedTestMethods.size(), pass, failed);
    }
}

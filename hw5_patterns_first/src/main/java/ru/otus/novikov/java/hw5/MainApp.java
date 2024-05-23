package ru.otus.novikov.java.hw5;

import java.util.Arrays;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        Product newProduct = Product.builder()
            .id(123L)
            .title("title")
            .description("description")
            .cost(12.2)
            .weight(123L)
            .width(123L)
            .height(123L)
            .length(123L)
            .build();
        System.out.println(newProduct);

        List<String> secondList = Arrays.asList("second1", "second2");

        Box box = Box.builder()
            .firstList(null)
            .secondList(secondList)
            .thirdList(List.of("third1", "third2"))
            .fourthList(List.of("fourth1", "fourth2"))
            .build();

        for (String str: box) {
            System.out.println(str);
        }
    }
}

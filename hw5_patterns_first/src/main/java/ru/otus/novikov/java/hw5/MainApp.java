package ru.otus.novikov.java.hw5;

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

        Box box = new Box(List.of("first1", "first2"), List.of("second1", "second2"), List.of("third1", "third2"), List.of("fourth1", "fourth2"));

        for (String str: box) {
            System.out.println(str);
        }
    }
}

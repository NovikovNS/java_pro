package ru.otus.novikov.java.hw6.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
public class Book {
    @EqualsAndHashCode.Exclude
    private final int id;

    private final String name;

    private final String author;

    private final String style;
}

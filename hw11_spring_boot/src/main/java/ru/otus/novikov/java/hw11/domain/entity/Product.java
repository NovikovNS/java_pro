package ru.otus.novikov.java.hw11.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    private Long id;

    private String title;

    private Long price;
}

package ru.otus.novikov.java.hw11.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    private Long id;

    private String title;

    private Long price;
}

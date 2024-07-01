package ru.otus.novikov.java.hw13_jpql.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PhoneDto {
    private Long id;

    private String number;
}

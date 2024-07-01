package ru.otus.novikov.java.hw13_jpql.rest.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateRequestClient {
    private String name;

    private AddressDto address;

    private List<PhoneDto> phones;

}

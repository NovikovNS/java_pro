package ru.otus.novikov.java.hw13_jpql.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class ClientDto {
    private Long id;

    private String name;

    private AddressDto address;

    private List<PhoneDto> phones;
}

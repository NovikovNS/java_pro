package ru.otus.novikov.java.hw12.service;

import ru.otus.novikov.java.hw12.rest.dto.CustomerDto;

import java.util.List;

public interface ProductService {
    List<CustomerDto> getCustomersByProductId(Long productId);
    void deleteProduct(Long productId);
}

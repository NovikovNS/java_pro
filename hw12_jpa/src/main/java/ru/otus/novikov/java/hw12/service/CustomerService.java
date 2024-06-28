package ru.otus.novikov.java.hw12.service;

import ru.otus.novikov.java.hw12.rest.dto.ProductDto;

import java.util.List;

public interface CustomerService {
    List<ProductDto> getCustomerProducts(Long customerId);

    void deleteCustomer(Long customerId);
}

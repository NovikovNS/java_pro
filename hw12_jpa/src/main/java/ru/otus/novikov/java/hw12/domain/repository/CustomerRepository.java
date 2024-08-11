package ru.otus.novikov.java.hw12.domain.repository;

import ru.otus.novikov.java.hw12.domain.entity.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> getCustomer(Long customerId);

    void deleteCustomer(Long customerId);
}

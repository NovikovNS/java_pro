package ru.otus.novikov.java.hw12.domain.repository;

import ru.otus.novikov.java.hw12.domain.entity.Product;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> getProduct(Long productId);

    void deleteProduct(Long productId);
}

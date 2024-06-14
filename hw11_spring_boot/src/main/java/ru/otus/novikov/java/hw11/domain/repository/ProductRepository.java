package ru.otus.novikov.java.hw11.domain.repository;

import ru.otus.novikov.java.hw11.domain.entity.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> getAllProducts();
    Product getProduct(Long id);
    Product createProduct(Product product);
    void delete(Long Id);
}

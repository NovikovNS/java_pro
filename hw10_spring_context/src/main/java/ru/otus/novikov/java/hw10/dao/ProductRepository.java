package ru.otus.novikov.java.hw10.dao;

import ru.otus.novikov.java.hw10.domain.Product;

import java.util.List;

public interface ProductRepository {
    public List<Product> getAllProducts();
    public Product getProductById(Long id);
}

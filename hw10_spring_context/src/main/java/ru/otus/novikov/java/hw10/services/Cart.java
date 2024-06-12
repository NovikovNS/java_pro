package ru.otus.novikov.java.hw10.services;

import ru.otus.novikov.java.hw10.domain.Product;

import java.util.List;

public interface Cart {
    void addProduct(Product product);
    void deleteProduct(Product product);
    List<Product> getCartProducts();
}

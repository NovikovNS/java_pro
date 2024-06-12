package ru.otus.novikov.java.hw10.services;

import ru.otus.novikov.java.hw10.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class CartImpl implements Cart {
    private final List<Product> cartProducts = new ArrayList<>();

    @Override
    public void addProduct(Product product) {
        cartProducts.add(product);
    }

    @Override
    public void deleteProduct(Product product) {
        cartProducts.remove(product);
    }

    @Override
    public List<Product> getCartProducts() {
        return cartProducts;
    }
}

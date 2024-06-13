package ru.otus.novikov.java.hw10.dao;

import ru.otus.novikov.java.hw10.domain.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {

    private final List<Product> products;

    public ProductRepositoryImpl() {
        products = new ArrayList<>();
    }

    @PostConstruct
    public void initProducts() {
        products.add(new Product(1L, "Test1", 20L));
        products.add(new Product(2L, "Test2", 20L));
        products.add(new Product(3L, "Test3", 20L));
        products.add(new Product(4L, "Test4", 20L));
        products.add(new Product(5L, "Test5", 20L));
        products.add(new Product(6L, "Test6", 20L));
        products.add(new Product(7L, "Test7", 20L));
        products.add(new Product(8L, "Test8", 20L));
        products.add(new Product(9L, "Test9", 20L));
        products.add(new Product(10L, "Test10", 20L));
    }

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public Product getProductById(Long id) {
        return products.stream().filter(product -> product.getId().equals(id)).findFirst().orElseThrow(RuntimeException::new);
    }
}

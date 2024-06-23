package ru.otus.novikov.java.hw11.service;

import ru.otus.novikov.java.hw11.rest.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();
    ProductDto getProduct(Long productId);
    ProductDto createProduct(ProductDto productDto);
    void delete(Long productId);
}

package ru.otus.novikov.java.hw11.service;

import lombok.RequiredArgsConstructor;
import ru.otus.novikov.java.hw11.domain.entity.Product;
import ru.otus.novikov.java.hw11.domain.repository.ProductRepository;
import ru.otus.novikov.java.hw11.rest.dto.ProductDto;
import ru.otus.novikov.java.hw11.util.Mapper;

import java.util.List;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final Mapper<Product, ProductDto> mapper;

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.getAllProducts().stream().map(mapper::mapToDto).toList();
    }

    @Override
    public ProductDto getProduct(Long productId) {
        return mapper.mapToDto(productRepository.getProduct(productId));
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = mapper.mapToEntity(productDto);
        return mapper.mapToDto(productRepository.createProduct(product));
    }

    @Override
    public void delete(Long productId) {
        productRepository.delete(productId);
    }
}

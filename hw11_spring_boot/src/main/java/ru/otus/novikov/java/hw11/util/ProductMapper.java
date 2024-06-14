package ru.otus.novikov.java.hw11.util;

import org.springframework.stereotype.Service;
import ru.otus.novikov.java.hw11.domain.entity.Product;
import ru.otus.novikov.java.hw11.rest.dto.ProductDto;

@Service
public class ProductMapper implements Mapper<Product, ProductDto>{
    @Override
    public ProductDto mapToDto(Product entity) {
        return ProductDto.builder()
            .id(entity.getId())
            .title(entity.getTitle())
            .price(entity.getPrice())
            .build();
    }

    @Override
    public Product mapToEntity(ProductDto dto) {
        return Product.builder()
            .id(dto.getId())
            .title(dto.getTitle())
            .price(dto.getPrice())
            .build();
    }
}

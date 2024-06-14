package ru.otus.novikov.java.hw11.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.novikov.java.hw11.domain.entity.Product;
import ru.otus.novikov.java.hw11.domain.repository.ProductRepository;
import ru.otus.novikov.java.hw11.rest.dto.ProductDto;
import ru.otus.novikov.java.hw11.service.ProductService;
import ru.otus.novikov.java.hw11.service.ProductServiceImpl;
import ru.otus.novikov.java.hw11.util.Mapper;

@Configuration
public class ServiceAutoConfiguration {

    @Bean
    public ProductService productService(ProductRepository productRepository,
        Mapper<Product, ProductDto> productMapper) {
        return new ProductServiceImpl(productRepository, productMapper);
    }
}

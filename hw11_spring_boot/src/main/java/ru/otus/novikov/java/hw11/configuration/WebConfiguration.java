package ru.otus.novikov.java.hw11.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.novikov.java.hw11.rest.ProductController;
import ru.otus.novikov.java.hw11.service.ProductService;

@Configuration
public class WebConfiguration {
    @Bean
    public ProductController productController(ProductService productService) {
        return new ProductController(productService);
    }
}

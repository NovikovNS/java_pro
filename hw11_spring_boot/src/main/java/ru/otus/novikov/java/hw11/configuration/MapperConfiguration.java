package ru.otus.novikov.java.hw11.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.novikov.java.hw11.domain.entity.Product;
import ru.otus.novikov.java.hw11.rest.dto.ProductDto;
import ru.otus.novikov.java.hw11.util.Mapper;
import ru.otus.novikov.java.hw11.util.ProductMapper;

@Configuration
public class MapperConfiguration {
    @Bean
    public Mapper<Product, ProductDto> productMapper() {
        return new ProductMapper();
    }
}

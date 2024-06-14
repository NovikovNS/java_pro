package ru.otus.novikov.java.hw11.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.novikov.java.hw11.domain.repository.ProductInMemoryRepository;
import ru.otus.novikov.java.hw11.domain.repository.ProductRepository;

@Configuration
public class RepositoryConfiguration {

    @Bean
    public ProductRepository productRepository(){
        return new ProductInMemoryRepository();
    }

}

package ru.otus.novikov.java.hw10.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.otus.novikov.java.hw10.dao.ProductRepository;
import ru.otus.novikov.java.hw10.dao.ProductRepositoryImpl;
import ru.otus.novikov.java.hw10.services.Cart;
import ru.otus.novikov.java.hw10.services.CartImpl;
import ru.otus.novikov.java.hw10.services.IOService;
import ru.otus.novikov.java.hw10.services.IOServiceImpl;
import ru.otus.novikov.java.hw10.services.StoreService;
import ru.otus.novikov.java.hw10.services.StoreServiceImpl;

@Configuration
public class AppConfig {

    @Bean
    @Scope(value = "prototype")
    public Cart cart() {
        return new CartImpl();
    }

    @Bean
    public ProductRepository productRepository() {
        return new ProductRepositoryImpl();
    }

    @Bean
    public StoreService storeService(ProductRepository productRepository, IOService ioService, Cart cart) {
        return new StoreServiceImpl(productRepository, ioService, cart);
    }

    @Bean
    public IOService ioService() {
        return new IOServiceImpl();
    }
}

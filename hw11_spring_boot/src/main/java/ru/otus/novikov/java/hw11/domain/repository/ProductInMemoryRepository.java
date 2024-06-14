package ru.otus.novikov.java.hw11.domain.repository;

import ru.otus.novikov.java.hw11.domain.entity.Product;
import ru.otus.novikov.java.hw11.exceptions.EntityNotFoundException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ProductInMemoryRepository implements ProductRepository {
    private List<Product> products;
    private AtomicLong idCounter;

    @PostConstruct
    public void initProducts() {
        this.idCounter = new AtomicLong(1);
        this.products = new ArrayList<>();
        products.add(Product.builder().id(idCounter.getAndIncrement()).title("title1").price(100L).build());
        products.add(Product.builder().id(idCounter.getAndIncrement()).title("title2").price(100L).build());
        products.add(Product.builder().id(idCounter.getAndIncrement()).title("title3").price(100L).build());
        products.add(Product.builder().id(idCounter.getAndIncrement()).title("title4").price(100L).build());
        products.add(Product.builder().id(idCounter.getAndIncrement()).title("title5").price(100L).build());
        products.add(Product.builder().id(idCounter.getAndIncrement()).title("title6").price(100L).build());
        products.add(Product.builder().id(idCounter.getAndIncrement()).title("title7").price(100L).build());
        products.add(Product.builder().id(idCounter.getAndIncrement()).title("title8").price(100L).build());
        products.add(Product.builder().id(idCounter.getAndIncrement()).title("title9").price(100L).build());
    }

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public Product getProduct(Long id) {
        return products.stream().filter(product -> product.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new EntityNotFoundException(String.format("Не найден продукт с идентификатором %s", id)));
    }

    @Override
    public Product createProduct(Product product) {
        product.setId(idCounter.getAndIncrement());
        products.add(product);
        return product;
    }

    @Override
    public void delete(Long id) {
        products.removeIf(it -> it.getId().equals(id));
    }
}

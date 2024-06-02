package ru.otus.novikov.java.hw7;

import com.zaxxer.hikari.HikariDataSource;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.novikov.java.hw7.dao.AbstractRepository;
import ru.otus.novikov.java.hw7.domain.Product;

import javax.sql.DataSource;

public class MainApp {

    private static Logger log = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) {

        DataSource source = createSource();

        AbstractRepository<Product> repo = new AbstractRepository<>(source, Product.class);

        Product product = Product.builder().price(1).title("title").build();
        Product productForUpdate = Product.builder().id(1L).price(1123123).title("updatedTitle").build();

        val foundProductsBeforeCreate = repo.findAll();
        System.out.printf("foundProductsBeforeCreate: %s \n", foundProductsBeforeCreate);
        repo.create(product);
        val foundProductsAfterCreate = repo.findAll();
        System.out.printf("foundProductsAfterCreate: %s \n", foundProductsAfterCreate);

        val productBeforeUpdate = repo.findById(1L);
        System.out.printf("productBeforeUpdate: %s \n", productBeforeUpdate);
        repo.update(productForUpdate);
        val productAfterUpdate = repo.findById(1L);
        System.out.printf("productAfterUpdate: %s \n", productAfterUpdate);

        repo.deleteById(1L);
    }

    private static DataSource createSource() {
        HikariDataSource source = new HikariDataSource();
        source.setJdbcUrl("jdbc:h2:file:./db;MODE=PostgreSQL;INIT=RUNSCRIPT FROM 'classpath:init.sql';");
        return source;
    }
}

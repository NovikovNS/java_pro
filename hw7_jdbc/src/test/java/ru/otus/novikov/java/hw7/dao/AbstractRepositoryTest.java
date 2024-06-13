package ru.otus.novikov.java.hw7.dao;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.novikov.java.hw7.domain.Product;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractRepositoryTest {

    DataSource source = createSource();
    AbstractRepository<Product> repo = new AbstractRepository<>(source, Product.class);

    private static DataSource createSource() {
        HikariDataSource source = new HikariDataSource();
        source.setJdbcUrl("jdbc:h2:file:./dbTest;MODE=PostgreSQL;INIT=RUNSCRIPT FROM 'classpath:init.sql';");
        source.setTransactionIsolation("TRANSACTION_REPEATABLE_READ");
        return source;
    }

    @BeforeEach
    public void setUp() {
        deleteAll();
        try {
            // TODO без этого таймаута не работают тесты. Кажется, что удаление данных влияет на создание
            // TODO без таймату также не успевает отработать update
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        createTestDate();
    }

    @Test
    public void findProductById() {
        Product product = repo.findById(1L);
        assertThat(product).isEqualTo(Product.builder().id(1L).title("title1").price(2).build());
    }

    @Test
    public void findAllProducts() {
        List<Product> products = repo.findAll();
        assertThat(products.size()).isEqualTo(2);
    }

    @Test
    public void createProduct() {
        try {
            List<Product> productsBefore = new ArrayList<>();
            List<Product> productsAfter = new ArrayList<>();
            getAllProducts(productsBefore);
            assertThat(productsBefore.size()).isEqualTo(2);
            repo.create(Product.builder().id(3L).title("title3").price(3).build());
            getAllProducts(productsAfter);
            assertThat(productsAfter.size()).isEqualTo(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void updateProduct() {
        Product dataToUpdate = Product.builder().id(1L).title("updatedTitle").price(123).build();
        Product productBeforeUpdate = getProductById(dataToUpdate.getId());
        repo.update(dataToUpdate);
        Product productAfterUpdate = getProductById(dataToUpdate.getId());
        assertThat(productAfterUpdate.getId()).isEqualTo(dataToUpdate.getId());
        assertThat(productAfterUpdate.getTitle()).isEqualTo(dataToUpdate.getTitle());
        assertThat(productAfterUpdate.getPrice()).isEqualTo(dataToUpdate.getPrice());
    }

    @Test
    public void deleteProductById() {
        try {
            List<Product> productsBefore = new ArrayList<>();
            List<Product> productsAfter = new ArrayList<>();
            getAllProducts(productsBefore);
            assertThat(productsBefore.size()).isEqualTo(2);
            repo.deleteById(2L);
            getAllProducts(productsAfter);
            assertThat(productsAfter.size()).isEqualTo(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteAllProduct() {
        try {
            List<Product> productsBefore = new ArrayList<>();
            List<Product> productsAfter = new ArrayList<>();
            getAllProducts(productsBefore);
            assertThat(productsBefore.size()).isEqualTo(2);
            repo.deleteAll();
            getAllProducts(productsAfter);
            assertThat(productsAfter.size()).isEqualTo(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getAllProducts(List<Product> productsBefore) throws SQLException {
        ResultSet rs = source.getConnection().createStatement().executeQuery("SELECT * FROM PRODUCTS");
        while (rs.next()) {
            productsBefore.add(Product.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .price(rs.getInt("price")).build());
        }
    }

    private void deleteAll() {
        try {
            source.getConnection().createStatement().executeUpdate("DELETE FROM PRODUCTS");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTestDate() {
        try {
            PreparedStatement ps = source.getConnection()
                .prepareStatement("insert into products (id, title, price) values (?, ?, ?)");
            List<Product> products = List.of(Product.builder().id(1L).title("title1").price(2).build(),
                Product.builder().id(2L).title("title2").price(4).build());
            for (Product product : products) {
                ps.setLong(1, product.getId());
                ps.setString(2, product.getTitle());
                ps.setInt(3, product.getPrice());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Product getProductById(Long id) {
        Product product = null;
        try {
            ResultSet rs;
            rs = source.getConnection().createStatement().executeQuery(String.format("SELECT * FROM PRODUCTS WHERE ID = %s", id));
            while (rs.next()) {
                product= Product.builder()
                    .id(rs.getLong("id"))
                    .title(rs.getString("title"))
                    .price(rs.getInt("price")).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }
}

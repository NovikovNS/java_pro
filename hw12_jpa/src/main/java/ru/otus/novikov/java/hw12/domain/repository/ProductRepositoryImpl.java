package ru.otus.novikov.java.hw12.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.novikov.java.hw12.domain.entity.Customer;
import ru.otus.novikov.java.hw12.domain.entity.Product;
import ru.otus.novikov.java.hw12.exceptions.EntityNotFoundException;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Product> getProduct(Long productId) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("products-customer");
        TypedQuery<Product> query = entityManager.createQuery("SELECT P from Product P where P.id =:productId", Product.class);
        query.setParameter("productId", productId);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public void deleteProduct(Long productId) {
        getProduct(productId).map(product -> {
            for (Customer customer: product.getCustomers()) {
                customer.removeProduct(product);
            }
            entityManager.remove(product);
            return true;
        }).orElseThrow(() -> new EntityNotFoundException(String.format("Not found product with id: %s", productId)));
    }
}

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
public class CustomerRepositoryImpl implements CustomerRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Customer> getCustomer(Long customerId) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("customer-products");
        TypedQuery<Customer> query = entityManager.createQuery("SELECT C from Customer C where C.id =:customerId", Customer.class);
        query.setParameter("customerId", customerId);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public void deleteCustomer(Long customerId) {
        getCustomer(customerId).map(customer -> {
            for (Product product: customer.getProducts()) {
                product.removeCustomer(customer);
            }
            entityManager.remove(customer);
            return true;
        }).orElseThrow(() -> new EntityNotFoundException(String.format("Not found customer with id: %s", customerId)));
    }
}

package ru.otus.novikov.java.hw13_jpql.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.novikov.java.hw13_jpql.domain.entity.Client;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public void createClient(Client client) {
        entityManager.persist(client);
    }

    @Override
    public Optional<Client> getClient(Long clientId) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("client-address-phone");
        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.fetchgraph", entityGraph);
        return Optional.ofNullable(entityManager.find(Client.class, clientId, properties));
    }
}

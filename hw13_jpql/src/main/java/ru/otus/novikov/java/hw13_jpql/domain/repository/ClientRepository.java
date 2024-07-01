package ru.otus.novikov.java.hw13_jpql.domain.repository;

import ru.otus.novikov.java.hw13_jpql.domain.entity.Client;

import java.util.Optional;

public interface ClientRepository {
    void createClient(Client client);

    Optional<Client> getClient(Long clientId);
}

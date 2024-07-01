package ru.otus.novikov.java.hw13_jpql.service;

import ru.otus.novikov.java.hw13_jpql.rest.dto.ClientDto;
import ru.otus.novikov.java.hw13_jpql.rest.dto.CreateRequestClient;

public interface ClientService {
    void createClient(CreateRequestClient request);

    ClientDto getClient(Long clientId);
}

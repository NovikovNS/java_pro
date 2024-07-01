package ru.otus.novikov.java.hw13_jpql.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.novikov.java.hw13_jpql.domain.entity.Address;
import ru.otus.novikov.java.hw13_jpql.domain.entity.Client;
import ru.otus.novikov.java.hw13_jpql.domain.entity.Phone;
import ru.otus.novikov.java.hw13_jpql.domain.repository.ClientRepository;
import ru.otus.novikov.java.hw13_jpql.exceptions.EntityNotFoundException;
import ru.otus.novikov.java.hw13_jpql.rest.dto.AddressDto;
import ru.otus.novikov.java.hw13_jpql.rest.dto.ClientDto;
import ru.otus.novikov.java.hw13_jpql.rest.dto.CreateRequestClient;
import ru.otus.novikov.java.hw13_jpql.rest.dto.PhoneDto;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Transactional
    @Override
    public void createClient(CreateRequestClient request) {
        Client client = Client.builder()
            .name(request.getName())
            .address(Address.builder()
                .street(request.getAddress().getStreet())
                .build())
            .build();
        request.getPhones().forEach(it -> client.addPhone(Phone.builder().number(it.getNumber()).build()));
        clientRepository.createClient(client);
    }

    @Override
    public ClientDto getClient(Long clientId) {
        Client client = clientRepository.getClient(clientId)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Not found client with id: %s", clientId)));
        Address address = client.getAddress();
        return ClientDto.builder()
            .id(client.getId())
            .name(client.getName())
            .address(new AddressDto(address.getId(), address.getStreet()))
            .phones(client.getPhones().stream().map(it -> new PhoneDto(it.getId(), it.getNumber())).toList())
            .build();
    }
}

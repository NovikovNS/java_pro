package ru.otus.novikov.java.hw13_jpql.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.novikov.java.hw13_jpql.rest.dto.ClientDto;
import ru.otus.novikov.java.hw13_jpql.rest.dto.CreateRequestClient;
import ru.otus.novikov.java.hw13_jpql.service.ClientService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    @PostMapping()
    public void createClient(@RequestBody CreateRequestClient request) {
        clientService.createClient(request);
    }

    @GetMapping(value = "/{clientId}", produces = "application/json;charset=utf-8")
    public ClientDto getClient(@PathVariable Long clientId) {
        return clientService.getClient(clientId);
    }
}

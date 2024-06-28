package ru.otus.novikov.java.hw12.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.novikov.java.hw12.domain.repository.CustomerRepository;
import ru.otus.novikov.java.hw12.exceptions.EntityNotFoundException;
import ru.otus.novikov.java.hw12.rest.dto.ProductDto;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public List<ProductDto> getCustomerProducts(Long customerId) {
        return customerRepository.getCustomer(customerId)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Not found customer with id: %s", customerId)))
            .getProducts().stream().map(product ->
                ProductDto.builder()
                    .id(product.getId())
                    .title(product.getTitle())
                    .price(product.getPrice())
                    .build())
            .toList();
    }

    @Transactional
    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteCustomer(customerId);
    }
}

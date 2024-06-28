package ru.otus.novikov.java.hw12.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.novikov.java.hw12.domain.repository.ProductRepository;
import ru.otus.novikov.java.hw12.exceptions.EntityNotFoundException;
import ru.otus.novikov.java.hw12.rest.dto.CustomerDto;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<CustomerDto> getCustomersByProductId(Long productId) {
        return productRepository.getProduct(productId)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Not found product with id: %s", productId)))
            .getCustomers().stream().map(entity ->
                CustomerDto.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build())
            .toList();
    }

    @Transactional
    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteProduct(productId);
    }
}

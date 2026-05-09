package com.acme.ordermanagement.product.service;

import com.acme.ordermanagement.common.exception.ResourceAlreadyExistsException;
import com.acme.ordermanagement.product.entity.Product;
import com.acme.ordermanagement.product.repository.ProductRepository;
import com.acme.ordermanagement.product.dto.CreateProductRequest;
import com.acme.ordermanagement.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {
        productRepository.findBySku(request.sku())
                .ifPresent(existing -> {
                    throw new ResourceAlreadyExistsException(
                            "Product already exists with sku: " + request.sku()
                    );
                });

        Product product = new Product(
                request.sku(),
                request.name(),
                request.unitPrice(),
                request.availableQuantity()
        );

        Product saved = productRepository.save(product);

        return new ProductResponse(
                saved.getId(),
                saved.getSku(),
                saved.getName(),
                saved.getUnitPrice(),
                saved.getAvailableQuantity()
        );
    }
}
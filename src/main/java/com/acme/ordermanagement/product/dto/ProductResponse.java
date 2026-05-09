package com.acme.ordermanagement.product.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String sku,
        String name,
        BigDecimal unitPrice,
        Integer availableQuantity
) {
}
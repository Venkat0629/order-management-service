package com.acme.ordermanagement.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank(message = "SKU is required")
        String sku,

        @NotBlank(message = "Product name is required")
        String name,

        @NotNull(message = "Unit price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than zero")
        BigDecimal unitPrice,

        @NotNull(message = "Available quantity is required")
        @Min(value = 0, message = "Available quantity cannot be negative")
        Integer availableQuantity
) {
}
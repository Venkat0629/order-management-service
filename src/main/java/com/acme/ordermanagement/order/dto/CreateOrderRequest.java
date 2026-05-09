package com.acme.ordermanagement.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(
        @NotNull(message = "Customer id is required")
        Long customerId,

        @Valid
        @NotEmpty(message = "Order must contain at least one item")
        List<OrderItemRequest> items
) {
}
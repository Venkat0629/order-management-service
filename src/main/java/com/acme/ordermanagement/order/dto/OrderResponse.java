package com.acme.ordermanagement.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        String orderNumber,
        Long customerId,
        OrderStatus status,
        BigDecimal totalAmount,
        LocalDateTime createdAt
) {
}
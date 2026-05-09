package com.acme.ordermanagement.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderSummaryResponse(
        String orderNumber,
        String customerName,
        BigDecimal totalAmount,
        OrderStatus status,
        LocalDateTime createdAt
) {
}
package com.acme.ordermanagement.customer.dto;

public record CustomerResponse(
        Long id,
        String fullName,
        String email
) {
}
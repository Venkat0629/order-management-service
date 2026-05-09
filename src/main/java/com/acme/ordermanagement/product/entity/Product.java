package com.acme.ordermanagement.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private Integer availableQuantity;

    public Product(String sku, String name, BigDecimal unitPrice, Integer availableQuantity) {
        this.sku = sku;
        this.name = name;
        this.unitPrice = unitPrice;
        this.availableQuantity = availableQuantity;
    }

    public void reserve(int quantity) {
        if (availableQuantity < quantity) {
            throw new IllegalArgumentException("Insufficient stock for sku: " + sku);
        }
        this.availableQuantity -= quantity;
    }
}
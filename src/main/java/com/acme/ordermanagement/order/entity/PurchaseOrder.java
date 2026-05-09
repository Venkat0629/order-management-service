package com.acme.ordermanagement.order.entity;

import com.acme.ordermanagement.customer.entity.Customer;
import com.acme.ordermanagement.order.dto.OrderStatus;
import com.acme.ordermanagement.product.entity.OrderItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase_orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public PurchaseOrder(String orderNumber, Customer customer) {
        this.orderNumber = orderNumber;
        this.customer = customer;
        this.status = OrderStatus.CREATED;
        this.totalAmount = BigDecimal.ZERO;
        this.createdAt = LocalDateTime.now();
    }

    public void addItem(OrderItem item) {
        items.add(item);
        this.totalAmount = this.totalAmount.add(
                item.getPriceAtPurchase().multiply(BigDecimal.valueOf(item.getQuantity()))
        );
    }

    public void markPaid() {
        this.status = OrderStatus.PAID;
    }
}
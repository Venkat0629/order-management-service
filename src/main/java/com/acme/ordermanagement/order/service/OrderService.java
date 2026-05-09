package com.acme.ordermanagement.order.service;

import com.acme.ordermanagement.common.exception.ResourceNotFoundException;
import com.acme.ordermanagement.customer.entity.Customer;
import com.acme.ordermanagement.customer.repository.CustomerRepository;
import com.acme.ordermanagement.order.dto.*;
import com.acme.ordermanagement.order.entity.PurchaseOrder;
import com.acme.ordermanagement.order.repository.PurchaseOrderRepository;
import com.acme.ordermanagement.product.entity.OrderItem;
import com.acme.ordermanagement.product.entity.Product;
import com.acme.ordermanagement.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with id: " + request.customerId()
                ));

        PurchaseOrder order = new PurchaseOrder(
                "ORD-" + System.currentTimeMillis(),
                customer
        );

        for (OrderItemRequest itemRequest : request.items()) {
            Product product = productRepository.findById(itemRequest.productId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Product not found with id: " + itemRequest.productId()
                    ));

            product.reserve(itemRequest.quantity());

            OrderItem orderItem = new OrderItem(
                    order,
                    product,
                    itemRequest.quantity(),
                    product.getUnitPrice()
            );

            order.addItem(orderItem);
        }

        PurchaseOrder saved = purchaseOrderRepository.save(order);

        return new OrderResponse(
                saved.getId(),
                saved.getOrderNumber(),
                saved.getCustomer().getId(),
                saved.getStatus(),
                saved.getTotalAmount(),
                saved.getCreatedAt()
        );
    }

    @Transactional
    public OrderResponse markOrderPaid(String orderNumber) {
        PurchaseOrder order = purchaseOrderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with order number: " + orderNumber
                ));

        order.markPaid();

        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getCustomer().getId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getCreatedAt()
        );
    }


    @Transactional(readOnly = true)
    public OrderDetailResponse getOrderDetails(String orderNumber) {
        PurchaseOrder order = purchaseOrderRepository.findDetailedByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with order number: " + orderNumber
                ));

        List<OrderItemResponse> items = order.getItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPriceAtPurchase()
                ))
                .toList();

        return new OrderDetailResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getCustomer().getId(),
                order.getCustomer().getFullName(),
                order.getCustomer().getEmail(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getCreatedAt(),
                items
        );
    }

    @Transactional(readOnly = true)
    public Page<OrderSummaryResponse> getOrdersByStatus(OrderStatus status, Pageable pageable) {
        return purchaseOrderRepository.findOrderSummariesByStatus(status, pageable);
    }

    @Transactional(readOnly = true)
    public List<OrderSummaryResponse> getOrdersByCustomer(Long customerId) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with id: " + customerId
                ));

        return purchaseOrderRepository.findOrderHistoryByCustomerId(customerId);
    }
}
package com.acme.ordermanagement.order.controller;

import com.acme.ordermanagement.order.dto.*;
import com.acme.ordermanagement.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @PatchMapping("/{orderNumber}/pay")
    public OrderResponse markOrderPaid(@PathVariable String orderNumber) {
        return orderService.markOrderPaid(orderNumber);
    }

    @GetMapping("/{orderNumber}")
    public OrderDetailResponse getOrderDetails(@PathVariable String orderNumber) {
        return orderService.getOrderDetails(orderNumber);
    }

    @GetMapping
    public Page<OrderSummaryResponse> getOrdersByStatus(
            @RequestParam OrderStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return orderService.getOrdersByStatus(status, PageRequest.of(page, size));
    }

    @GetMapping("/customer/{customerId}")
    public List<OrderSummaryResponse> getOrdersByCustomer(@PathVariable Long customerId) {
        return orderService.getOrdersByCustomer(customerId);
    }
}
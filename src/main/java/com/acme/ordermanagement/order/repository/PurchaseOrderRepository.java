package com.acme.ordermanagement.order.repository;

import com.acme.ordermanagement.order.dto.OrderStatus;
import com.acme.ordermanagement.order.entity.PurchaseOrder;
import com.acme.ordermanagement.order.dto.OrderSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    Optional<PurchaseOrder> findByOrderNumber(String orderNumber);

    @EntityGraph(attributePaths = {"customer", "items", "items.product"})
    @Query("select o from PurchaseOrder o where o.orderNumber = :orderNumber")
    Optional<PurchaseOrder> findDetailedByOrderNumber(String orderNumber);

    @Query("""
            select new com.acme.ordermanagement.order.dto.OrderSummaryResponse(
                o.orderNumber,
                c.fullName,
                o.totalAmount,
                o.status,
                o.createdAt
            )
            from PurchaseOrder o
            join o.customer c
            where o.status = :status
            """)
    Page<OrderSummaryResponse> findOrderSummariesByStatus(OrderStatus status, Pageable pageable);

    @Query("""
            select new com.acme.ordermanagement.order.dto.OrderSummaryResponse(
                o.orderNumber,
                c.fullName,
                o.totalAmount,
                o.status,
                o.createdAt
            )
            from PurchaseOrder o
            join o.customer c
            where c.id = :customerId
            order by o.createdAt desc
            """)
    List<OrderSummaryResponse> findOrderHistoryByCustomerId(Long customerId);
}
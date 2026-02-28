package com.ecommerce.service;

import com.ecommerce.model.entity.*;
import com.ecommerce.model.enums.OrderStatus;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    
@SuppressWarnings("null")
@Transactional
public Order createOrder(User user, List<OrderItem> orderItems, String shippingAddress) {
    // 1. Calculate total
    BigDecimal totalAmount = orderItems.stream()
        .map(OrderItem::getSubtotal)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    
    // 2. Create order
    Order order = Order.builder()
        .user(user)
        .orderDate(LocalDateTime.now())
        .totalAmount(totalAmount)
        .status(OrderStatus.PENDING)
        .shippingAddress(shippingAddress)
        .build();
    
    Order savedOrder = orderRepository.save(order);
    
    // 3. Save order items directly (transactional)
    orderItems.forEach(item -> {
        item.setOrder(savedOrder);
        orderItemRepository.save(item);  // Add this import
    });
    
    return savedOrder;
}

    @SuppressWarnings("null")
    @Transactional
    public Order confirmOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(OrderStatus.CONFIRMED);
        return orderRepository.save(order);
    }
}

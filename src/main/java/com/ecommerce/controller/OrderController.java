package com.ecommerce.controller;

import com.ecommerce.model.entity.Order;
import com.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    
    @SuppressWarnings("unused")
    private final OrderService orderService;
    
    @PostMapping
    public ResponseEntity<Order> createOrder(Authentication authentication) {
        // Simplified - full implementation in Step 8
        return ResponseEntity.ok(null);
    }
    
    @GetMapping("/user")
    public ResponseEntity<List<Order>> getUserOrders(Authentication authentication) {
        // Get orders for authenticated user
        return ResponseEntity.ok(null);
    }
}

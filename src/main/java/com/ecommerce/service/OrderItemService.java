package com.ecommerce.service;

import com.ecommerce.model.entity.OrderItem;
import com.ecommerce.model.entity.Product;
import com.ecommerce.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemService {
    
    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;
    
    public OrderItem save(OrderItem orderItem) {
        // Update product stock during order item creation
        Product product = orderItem.getProduct();
        productService.updateStock(product.getId(), orderItem.getQuantity());
        
        return orderItemRepository.save(orderItem);
    }
}

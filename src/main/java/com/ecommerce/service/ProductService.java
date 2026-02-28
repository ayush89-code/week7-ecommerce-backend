package com.ecommerce.service;

import com.ecommerce.exception.InsufficientStockException;
import com.ecommerce.model.entity.Product;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @SuppressWarnings("null")
    @Cacheable(value = "products", key = "#id")
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Page<Product> findAvailableProducts(Long categoryId, BigDecimal minPrice,
            BigDecimal maxPrice, Pageable pageable) {
        // Use custom query ONLY when filters are provided
        if (categoryId != null || minPrice != null || maxPrice != null) {
            return productRepository.findAvailableProducts(categoryId, minPrice, maxPrice, pageable);
        }
        // Default: active products only
        return productRepository.findByIsActiveTrue(pageable);
    }

    @Transactional
    public Product updateStock(Long productId, Integer quantity) {
        @SuppressWarnings("null")
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        int newStock = Math.max(0, product.getStockQuantity() - quantity);
        product.setStockQuantity(newStock);
        return productRepository.save(product);
    }
    
    @Transactional
    public void reserveStock(Long productId, Integer quantity) {
        @SuppressWarnings("null")
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        
        if (product.getStockQuantity() < quantity) {
            throw new InsufficientStockException(
                productId, 
                product.getStockQuantity(), 
                quantity
            );
        }
        
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);
    }
}

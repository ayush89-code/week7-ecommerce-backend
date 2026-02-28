package com.ecommerce.controller;

import com.ecommerce.model.entity.Product;
import com.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductService productService;
    
    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        // Safe sorting - only allow valid Product fields
        Sort sort;
        try {
            switch (sortBy.toLowerCase()) {
                case "id":
                case "name":
                case "price":
                case "stockquantity":
                    sort = sortDir.equalsIgnoreCase("desc") ? 
                        Sort.by(sortBy).descending() : 
                        Sort.by(sortBy).ascending();
                    break;
                default:
                    sort = Sort.by("name").ascending(); // Default safe sort
            }
        } catch (Exception e) {
            sort = Sort.by("name").ascending();
        }
        
        Pageable pageable = PageRequest.of(page, size, sort);
        
        // FIXED: Handle null parameters properly
        Page<Product> products = productService.findAvailableProducts(
            categoryId, minPrice, maxPrice, pageable
        );
        
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return productService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}

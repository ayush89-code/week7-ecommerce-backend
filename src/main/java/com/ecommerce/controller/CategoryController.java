package com.ecommerce.controller;

import com.ecommerce.model.entity.Category;
import com.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryRepository categoryRepository;
    
    @GetMapping
    public ResponseEntity<Page<Category>> getCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name) {
        
        PageRequest pageable = PageRequest.of(page, size);
        
        Page<Category> categories;
        if (name != null && !name.isEmpty()) {
            categories = categoryRepository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            categories = categoryRepository.findByParentIdIsNull(pageable);
        }
        
        return ResponseEntity.ok(categories);
    }
    
    @SuppressWarnings("null")
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        return categoryRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}

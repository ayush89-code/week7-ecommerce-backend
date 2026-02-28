package com.ecommerce.repository;

import com.ecommerce.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    // ✅ Pagination for active categories
    Page<Category> findByParentIdIsNull(Pageable pageable);
    
    // ✅ Find by name (search)
    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);
}

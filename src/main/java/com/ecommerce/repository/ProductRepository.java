package com.ecommerce.repository;

import com.ecommerce.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Filtering
    List<Product> findByIsActiveTrue();
    Optional<Product> findBySku(String sku);
    
    // Price range
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    // Pagination + Filtering
    Page<Product> findByIsActiveTrue(Pageable pageable);
    
    // Custom complex query
   
@Query("SELECT p FROM Product p WHERE p.isActive = true " +
       "AND (:categoryId IS NULL OR p.category.id = :categoryId) " +
       "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
       "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
Page<Product> findAvailableProducts(@Param("categoryId") Long categoryId,
                                  @Param("minPrice") BigDecimal minPrice,
                                  @Param("maxPrice") BigDecimal maxPrice,
                                  Pageable pageable);

}

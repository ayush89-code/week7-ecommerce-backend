package com.ecommerce.repository;

import com.ecommerce.model.entity.Order;
import com.ecommerce.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    // User orders
    List<Order> findByUserIdOrderByOrderDateDesc(Long userId);
    Page<Order> findByUserId(Long userId, Pageable pageable);
    
    // Status filtering
    List<Order> findByStatus(OrderStatus status);
    
    // Complex joins query
    @Query("SELECT o FROM Order o JOIN FETCH o.user u " +
           "WHERE o.user.id = :userId AND o.status = :status " +
           "ORDER BY o.orderDate DESC")
    List<Order> findByUserIdAndStatusWithUser(@Param("userId") Long userId, 
                                            @Param("status") OrderStatus status);
    
    @Query("SELECT o FROM Order o " +
           "WHERE o.orderDate BETWEEN :startDate AND :endDate " +
           "AND o.status = :status")
    List<Order> findByDateRangeAndStatus(@Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate,
                                       @Param("status") OrderStatus status);
}

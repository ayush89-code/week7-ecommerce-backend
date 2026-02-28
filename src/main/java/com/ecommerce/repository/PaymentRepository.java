package com.ecommerce.repository;

import com.ecommerce.model.entity.Payment;
import com.ecommerce.model.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByTransactionId(String transactionId);
    Optional<Payment> findByOrderId(Long orderId);

    // Transaction management queries
    @Query("SELECT p FROM Payment p WHERE p.status = :status AND p.createdAt >= :fromDate")
    List<Payment> findRecentFailedPayments(@Param("status") PaymentStatus status,
            @Param("fromDate") LocalDateTime fromDate);

    List<Payment> findByUserId(Long userId);
    List<Payment> findByPaymentDateAfter(LocalDateTime date);
    Long countByStatus(PaymentStatus status);

    // ✅ FIXED: Add @Query annotation
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.status = :status")
    BigDecimal sumAmountByStatus(@Param("status") PaymentStatus status);
}

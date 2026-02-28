package com.ecommerce.controller;

import com.ecommerce.exception.PaymentFailedException;
import com.ecommerce.model.dto.PaymentRequest;
import com.ecommerce.model.dto.PaymentResponse;
import com.ecommerce.model.entity.Order;
import com.ecommerce.model.entity.Payment;
import com.ecommerce.model.entity.User;
import com.ecommerce.model.enums.PaymentStatus;
import com.ecommerce.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentRepository paymentRepository;

@PostMapping("/process")
public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest request) {
    // ✅ Create dummy entities (for demo/testing)
    User dummyUser = new User();
    dummyUser.setId(1L);
    
    Order dummyOrder = new Order();
    dummyOrder.setId(1L);
    
    Payment payment = Payment.builder()
        .amount(request.getAmount())
        .paymentMethod(request.getPaymentMethod())
        .order(dummyOrder)
        .user(dummyUser)
        .transactionId("TXN_" + UUID.randomUUID().toString().substring(0, 8))
        .status(PaymentStatus.PENDING)  // ✅ Database accepts PENDING
        .build();
    
    payment.setPaymentDate(LocalDateTime.now());
    
    // ✅ Save FIRST as PENDING (passes constraint)
    Payment savedPayment = paymentRepository.save(payment);
    
    // ✅ Simulate payment processing
    boolean success = Math.random() > 0.3;
    
    if (success) {
        savedPayment.setStatus(PaymentStatus.SUCCESS);  // ✅ Update AFTER save
        paymentRepository.save(savedPayment);           // ✅ Second save works
        return ResponseEntity.ok(PaymentResponse.from(savedPayment));
    } else {
        savedPayment.setStatus(PaymentStatus.FAILED);
        paymentRepository.save(savedPayment);
        throw new PaymentFailedException(savedPayment.getTransactionId(), "Payment declined");
    }
}



    @SuppressWarnings("null")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable Long id) {
        return paymentRepository.findById(id)
                .map(payment -> ResponseEntity.ok(PaymentResponse.from(payment)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<PaymentResponse>> getPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        var pageable = PageRequest.of(page, size);
        var payments = paymentRepository.findAll(pageable);
        return ResponseEntity.ok(payments.map(PaymentResponse::from));
    }

    @PostMapping("/{id}/refund")
    public ResponseEntity<PaymentResponse> refundPayment(@PathVariable Long id) {
        @SuppressWarnings("null")
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (!payment.getStatus().equals(PaymentStatus.SUCCESS)) {
            throw new PaymentFailedException(payment.getTransactionId(), "Only SUCCESS payments can be refunded");
        }

        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setPaymentDate(LocalDateTime.now());
        return ResponseEntity.ok(PaymentResponse.from(paymentRepository.save(payment)));
    }

    @SuppressWarnings("null")
    @PostMapping("/{id}/cancel")
    public ResponseEntity<PaymentResponse> cancelPayment(@PathVariable Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (!payment.getStatus().equals(PaymentStatus.PENDING)) {
            throw new PaymentFailedException(payment.getTransactionId(), "Only PENDING payments can be cancelled");
        }

        payment.setStatus(PaymentStatus.CANCELLED);
        return ResponseEntity.ok(PaymentResponse.from(paymentRepository.save(payment)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentResponse>> getUserPayments(@PathVariable Long userId) {
        List<Payment> payments = paymentRepository.findByUserId(userId);
        return ResponseEntity.ok(payments.stream()
                .map(PaymentResponse::from)
                .collect(Collectors.toList()));
    }

    // ✅ USES YOUR sumAmountByStatus() method
    @GetMapping("/stats")
    public ResponseEntity<PaymentController.PaymentStats> getPaymentStats() {
        PaymentController.PaymentStats stats = new PaymentController.PaymentStats(
                paymentRepository.count(),
                paymentRepository.countByStatus(PaymentStatus.SUCCESS),
                paymentRepository.countByStatus(PaymentStatus.FAILED),
                paymentRepository.sumAmountByStatus(PaymentStatus.SUCCESS));
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<PaymentResponse>> getRecentPayments() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        List<Payment> payments = paymentRepository.findByPaymentDateAfter(thirtyDaysAgo);
        return ResponseEntity.ok(payments.stream()
                .map(PaymentResponse::from)
                .collect(Collectors.toList()));
    }

    // ✅ INNER CLASS
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentStats {
        private Long totalPayments;
        private Long successfulPayments;
        private Long failedPayments;
        private BigDecimal totalRevenue;
    }
}

package com.ecommerce.service;

import com.ecommerce.model.entity.Order;
import com.ecommerce.model.entity.Payment;
import com.ecommerce.model.enums.PaymentStatus;
import com.ecommerce.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    
    @SuppressWarnings("null")
    @Transactional
    public Payment processPayment(Order order, String paymentMethod, String transactionId) {
        Payment payment = Payment.builder()
            .order(order)
            .user(order.getUser())
            .amount(order.getTotalAmount())
            .paymentMethod(paymentMethod)
            .transactionId(transactionId)
            .status(PaymentStatus.COMPLETED)
            .paymentDate(LocalDateTime.now())
            .build();
        
        return paymentRepository.save(payment);
    }
    
    @Transactional
    public Payment markPaymentFailed(String transactionId) {
        Payment payment = paymentRepository.findByTransactionId(transactionId)
            .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(PaymentStatus.FAILED);
        return paymentRepository.save(payment);
    }
}

package com.ecommerce.model.dto;

import com.ecommerce.model.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {
    private Long id;
    private Long orderId;
    private Long userId;
    private BigDecimal amount;
    private String paymentMethod;
    private PaymentStatus status;
    private String transactionId;
    private LocalDateTime paymentDate;
    
    public static PaymentResponse from(com.ecommerce.model.entity.Payment payment) {
        return PaymentResponse.builder()
            .id(payment.getId())
            .orderId(payment.getOrder().getId())
            .userId(payment.getUser().getId())
            .amount(payment.getAmount())
            .paymentMethod(payment.getPaymentMethod())
            .status(payment.getStatus())
            .transactionId(payment.getTransactionId())
            .paymentDate(payment.getPaymentDate())
            .build();
    }
}

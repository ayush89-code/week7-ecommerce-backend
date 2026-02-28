package com.ecommerce.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    @NotNull(message = "orderId is required")
    private Long orderId;
    
    @NotNull(message = "userId is required") 
    private Long userId;
    
    @NotNull(message = "amount is required")
    @Positive(message = "amount must be positive")
    private BigDecimal amount;
    
    @NotNull(message = "paymentMethod is required")
    private String paymentMethod;
}

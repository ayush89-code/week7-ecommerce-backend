package com.ecommerce.model.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String sku;
    private String categoryName;  // Simple name only
    private String imageUrl;
    private Boolean isActive;
    private LocalDateTime createdAt;
}

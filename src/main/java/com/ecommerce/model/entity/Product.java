package com.ecommerce.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;  

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    
    @NotBlank
    @Size(max = 200)
    private String name;
    
    @Size(max = 500)
    private String description;
    
    @DecimalMin("0.0")
    @Column(nullable = false)
    private BigDecimal price;  // ✅ Now works
    
    
    @Min(0)
    @Column(name = "stock_quantity")
    @Builder.Default
    private Integer stockQuantity = 0;
    
    @Size(max = 100)
    @Column(unique = true)
    private String sku;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties({"parent", "hibernateLazyInitializer"})
    private Category category;
    
    @Column(name = "image_url")
    private String imageUrl;
    
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;
}

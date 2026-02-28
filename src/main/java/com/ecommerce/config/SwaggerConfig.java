package com.ecommerce.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "E-Commerce API",
        version = "1.0", 
        description = "Complete E-Commerce Backend API Documentation"
    )
)
public class SwaggerConfig {
    // ✅ EMPTY - SpringDoc auto-detects ALL controllers!
}

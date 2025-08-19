package com.velsystems.ecommerce.dto.request.product.create.variant;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductOptionValueRequest {
    private String value; // Example: "Black", "256GB"
}

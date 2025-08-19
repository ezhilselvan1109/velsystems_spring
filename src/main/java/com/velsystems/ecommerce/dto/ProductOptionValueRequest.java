package com.velsystems.ecommerce.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductOptionValueRequest {
    private String value; // Example: "Black", "256GB"
}

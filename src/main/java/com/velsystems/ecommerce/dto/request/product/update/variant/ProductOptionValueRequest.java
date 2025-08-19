package com.velsystems.ecommerce.dto.request.product.update.variant;

import lombok.*;

import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductOptionValueRequest {
    private UUID id;
    private String value;
}

package com.velsystems.ecommerce.dto.request.product.create.variant;

import lombok.*;

import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductVariantOptionRequest {
    private UUID optionValueId; // Example: uuid-black, uuid-256gb
}

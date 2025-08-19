package com.velsystems.ecommerce.dto.request.product.update.variant;

import lombok.*;

import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductVariantOptionRequest {
    private UUID id;
    private UUID optionValueId;
}

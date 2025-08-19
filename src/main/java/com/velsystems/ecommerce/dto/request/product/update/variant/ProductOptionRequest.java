package com.velsystems.ecommerce.dto.request.product.update.variant;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductOptionRequest {
    private UUID id;
    private String name;
    private List<ProductOptionValueRequest> values;
}

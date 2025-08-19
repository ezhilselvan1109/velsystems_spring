package com.velsystems.ecommerce.dto.request.product.create.variant;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductOptionRequest {
    private String name; // Example: "Color" / "Storage"
    private List<ProductOptionValueRequest> values;
}

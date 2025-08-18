package com.velsystems.ecommerce.dto.response.product.variant;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantOptionResponse {
    private UUID id;
    private String optionName; // ✅ service maps from option.getName()
    private String value;      // ✅ service maps from opt.getValue()
}

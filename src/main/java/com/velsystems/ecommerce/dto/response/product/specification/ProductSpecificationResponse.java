package com.velsystems.ecommerce.dto.response.product.specification;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSpecificationResponse {
    private UUID id;
    private String attributeName;   // ✅ service maps spec.getAttributeName()
    private String attributeValue;  // ✅ service maps spec.getAttributeValue()
}

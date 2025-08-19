package com.velsystems.ecommerce.dto.request.product.create.specification;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSpecificationRequest {
    private String attributeName;   // ✅ matches service
    private String attributeValue;  // ✅ matches service
}

package com.velsystems.ecommerce.dto.request;

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

package com.velsystems.ecommerce.dto.request.product.update.specification;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSpecificationRequest {
    private UUID id;
    private String attributeName;
    private String attributeValue;
}

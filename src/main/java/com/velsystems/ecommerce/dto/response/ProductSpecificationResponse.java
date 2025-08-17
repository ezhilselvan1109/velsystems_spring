package com.velsystems.ecommerce.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSpecificationResponse {
    private UUID id;
    private String attributeName;
    private String attributeValue;
}
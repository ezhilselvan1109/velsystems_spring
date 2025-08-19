package com.velsystems.ecommerce.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantOptionResponse {

    private UUID optionId;
    private String optionName;      // Example: Color, Storage
    private String optionValue;     // Example: Black, 256GB
}

package com.velsystems.ecommerce.dto.response.product;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageResponse {
    private UUID id;
    private String imageUrl;   // ✅ matches service
    private Boolean isPrimary; // ✅ matches service
    private Integer sortOrder; // ✅ matches service
}

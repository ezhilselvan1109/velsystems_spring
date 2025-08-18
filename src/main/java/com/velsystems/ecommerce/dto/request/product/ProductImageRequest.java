package com.velsystems.ecommerce.dto.request.product;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageRequest {
    private String imageUrl;   // ✅ matches service
    private Boolean isPrimary; // ✅ matches service
    private Integer sortOrder; // ✅ matches service
}

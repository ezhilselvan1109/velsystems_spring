package com.velsystems.ecommerce.dto.request.product.update;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageRequest {
    private UUID id;
    private String imageUrl;
    private Boolean isPrimary;
    private Integer sortOrder;
}

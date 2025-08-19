package com.velsystems.ecommerce.dto.request.product.update.variant;

import com.velsystems.ecommerce.dto.request.product.update.ProductImageRequest;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductVariantUpdateRequest {
    private UUID id;
    private String sku;
    private BigDecimal price;

    private List<ProductVariantOptionRequest> options;
    private List<ProductImageRequest> images;
}

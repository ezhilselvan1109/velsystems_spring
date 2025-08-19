package com.velsystems.ecommerce.dto.request.product.create.variant;

import com.velsystems.ecommerce.dto.request.product.create.ProductImageRequest;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductVariantCreateRequest {
    private String sku;
    private BigDecimal price;

    private List<ProductVariantOptionRequest> options;
    private List<ProductImageRequest> images;
}

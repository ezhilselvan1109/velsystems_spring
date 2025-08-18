package com.velsystems.ecommerce.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantRequest {
    private String sku;
    private BigDecimal price;

    private List<ProductVariantOptionRequest> options; // ✅ nested
    private List<ProductImageRequest> images;          // ✅ nested
}

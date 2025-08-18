package com.velsystems.ecommerce.dto.response.product.variant;

import com.velsystems.ecommerce.dto.response.product.ProductImageResponse;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantResponse {
    private UUID id;
    private String sku;
    private BigDecimal price;

    private List<ProductVariantOptionResponse> options; // ✅ nested
    private List<ProductImageResponse> images;          // ✅ nested
}

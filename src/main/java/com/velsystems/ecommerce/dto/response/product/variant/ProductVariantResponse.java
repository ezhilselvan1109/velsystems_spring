package com.velsystems.ecommerce.dto.response.product.variant;

import com.velsystems.ecommerce.dto.response.product.ProductImageResponse;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantResponse {

    private UUID id;
    private String sku;
    private Double price;
    private Integer stock;

    private Set<ProductVariantOptionResponse> options = new HashSet<>();
    private Set<ProductImageResponse> images = new HashSet<>();
}
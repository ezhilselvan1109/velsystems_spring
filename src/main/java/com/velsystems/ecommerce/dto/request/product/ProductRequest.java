package com.velsystems.ecommerce.dto.request.product;

import com.velsystems.ecommerce.dto.request.product.specification.ProductSpecificationGroupRequest;
import com.velsystems.ecommerce.dto.request.product.variant.ProductVariantRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String slug;

    private String description;

    @NotNull
    private UUID brandId;

    @NotNull
    private UUID categoryId;

    private List<ProductImageRequest> images;
    private List<ProductVariantRequest> variants;
    private List<ProductSpecificationGroupRequest> specificationGroups;
}

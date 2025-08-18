package com.velsystems.ecommerce.dto.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private UUID id;
    private String name;
    private String slug;
    private String description;

    private String brandName;
    private String categoryName;

    private List<ProductImageResponse> images;
    private List<ProductVariantResponse> variants;
    private List<ProductSpecificationGroupResponse> specificationGroups;
}

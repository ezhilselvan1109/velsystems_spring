package com.velsystems.ecommerce.dto;

import com.velsystems.ecommerce.dto.request.product.specification.ProductSpecificationGroupRequest;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductCreateRequest {
    private String name;
    private String slug;
    private String description;
    private UUID brandId;
    private UUID categoryId;
    private List<ProductOptionRequest> options;        // Options like Color, Storage
    private List<ProductSpecificationGroupRequest> specificationGroups; // Specs
}

package com.velsystems.ecommerce.dto.request.product.create;

import com.velsystems.ecommerce.dto.request.product.create.variant.ProductOptionRequest;
import com.velsystems.ecommerce.dto.request.product.create.specification.ProductSpecificationGroupRequest;
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

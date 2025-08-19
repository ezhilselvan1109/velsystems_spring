package com.velsystems.ecommerce.dto.request.product.update;

import com.velsystems.ecommerce.dto.request.product.update.specification.ProductSpecificationGroupRequest;
import com.velsystems.ecommerce.dto.request.product.update.variant.ProductOptionRequest;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductUpdateRequest {
    private String name;
    private String slug;
    private String description;
    private UUID brandId;
    private UUID categoryId;
    private List<ProductOptionRequest> options;
    private List<ProductSpecificationGroupRequest> specificationGroups;
}

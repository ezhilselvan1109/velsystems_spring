package com.velsystems.ecommerce.dto;

import com.velsystems.ecommerce.dto.response.BrandResponse;
import com.velsystems.ecommerce.dto.response.CategoryResponse;
import com.velsystems.ecommerce.dto.response.product.ProductImageResponse;
import com.velsystems.ecommerce.dto.response.product.specification.ProductSpecificationGroupResponse;
import com.velsystems.ecommerce.enums.Status;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
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
    private BrandResponse brand;
    private CategoryResponse category;
    private Status status;
    private Set<ProductOptionResponse> options = new HashSet<>();
    private Set<ProductSpecificationGroupResponse> specificationGroups = new HashSet<>();
    private Set<ProductVariantResponse> variants = new HashSet<>();
}


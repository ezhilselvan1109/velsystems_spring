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
    private BrandResponseDto brand;
    private ProductTypeResponse productType;
    private Double price;
    private Integer stock;
    private String description;
    private List<ProductImageResponseDto> image;
    private List<ProductSpecificationGroupResponse> specificationGroup;
}

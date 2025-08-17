package com.velsystems.ecommerce.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductUpdateRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Slug is required")
    private String slug;

    @NotNull(message = "Brand ID is required")
    private UUID brandId;

    @NotNull(message = "Product type ID is required")
    private UUID productTypeId;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price cannot be negative")
    private Double price;

    @NotNull(message = "Stock is required")
    @PositiveOrZero(message = "Stock cannot be negative")
    private Integer stock;

    private String description;

    private List<UUID> imageUrlIds; //for delete

    private List<ProductImageRequestDto> imageUrls; //for add

    private List<UUID> specificationGroupIds;

    private List<ProductSpecificationGroupRequest> specificationGroups;
}

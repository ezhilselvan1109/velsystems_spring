package com.velsystems.ecommerce.dto.response;

import com.velsystems.ecommerce.enums.CategoryStatus;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
    private UUID id;
    private String name;
    private String slug;
    private String description;
    private String imageUrl;
    private CategoryStatus status;
    private Integer sortOrder;
    private List<CategoryResponse> children;
}


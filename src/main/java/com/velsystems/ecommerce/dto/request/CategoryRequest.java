package com.velsystems.ecommerce.dto.request;

import com.velsystems.ecommerce.enums.CategoryStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {
    private String name;
    private String slug;
    private String description;
    private String imageUrl;
    private CategoryStatus status;
    private Integer sortOrder;
    private UUID parentId;
}


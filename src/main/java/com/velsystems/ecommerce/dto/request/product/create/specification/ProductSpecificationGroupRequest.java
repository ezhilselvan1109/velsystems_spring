package com.velsystems.ecommerce.dto.request.product.create.specification;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSpecificationGroupRequest {
    private String name;
    private List<ProductSpecificationRequest> specifications;
}

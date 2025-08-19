package com.velsystems.ecommerce.dto.response.product.variant;

import com.velsystems.ecommerce.dto.response.product.variant.ProductOptionValueResponse;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOptionResponse {
    private UUID id;
    private String name;
    private List<ProductOptionValueResponse> values;
}

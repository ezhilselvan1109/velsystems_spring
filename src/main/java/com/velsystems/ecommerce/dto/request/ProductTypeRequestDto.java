package com.velsystems.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductTypeRequestDto {

    @NotBlank(message = "ProductType name is required")
    private String name;

    @NotNull(message = "Category ID is required")
    private UUID categoryId;
}

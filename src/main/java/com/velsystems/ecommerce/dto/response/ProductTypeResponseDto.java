package com.velsystems.ecommerce.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductTypeResponseDto {

    private UUID id;
    private String name;
    private UUID categoryId;
    private String categoryName;
}

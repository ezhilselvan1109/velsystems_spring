package com.velsystems.ecommerce.dto.response;

import com.velsystems.ecommerce.enums.Status;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandResponse {
    private UUID id;
    private String name;
    private String description;
    private String logoUrl;
    private Status status;
    private Integer sortOrder;
}

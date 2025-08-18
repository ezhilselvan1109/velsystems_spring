package com.velsystems.ecommerce.dto.request;

import com.velsystems.ecommerce.enums.Status;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandRequest {
    private String name;
    private String description;
    private String logoUrl;
    private Status status;
    private Integer sortOrder;
}

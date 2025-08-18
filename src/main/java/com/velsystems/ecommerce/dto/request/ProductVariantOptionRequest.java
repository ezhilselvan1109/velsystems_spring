package com.velsystems.ecommerce.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantOptionRequest {
    private UUID optionId; // ✅ service uses optionRepository.findById(optReq.getOptionId())
    private String value;  // ✅ matches service
}

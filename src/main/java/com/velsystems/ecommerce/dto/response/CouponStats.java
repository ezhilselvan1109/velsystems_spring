package com.velsystems.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponStats {
    private Long activeCount;
    private Long totalUsedCount;
    private Long expiredCount;
}
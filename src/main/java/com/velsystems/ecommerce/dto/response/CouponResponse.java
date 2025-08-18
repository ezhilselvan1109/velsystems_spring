package com.velsystems.ecommerce.dto.response;

import com.velsystems.ecommerce.enums.CouponType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponResponse {
    private UUID id;
    private String code;
    private String description;
    private CouponType type;
    private BigDecimal value;
    private Boolean active;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private Integer maxUses;
    private Integer usedCount;
}

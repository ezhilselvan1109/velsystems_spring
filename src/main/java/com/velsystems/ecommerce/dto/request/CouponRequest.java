package com.velsystems.ecommerce.dto.request;

import com.velsystems.ecommerce.enums.CouponType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponRequest {

    @NotBlank(message = "Coupon code is required")
    @Size(max = 64, message = "Coupon code must be at most 64 characters")
    private String code;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description must be at most 255 characters")
    private String description;

    @NotNull(message = "Coupon type is required")
    private CouponType type;

    @NotNull(message = "Value is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Value must be greater than 0")
    private BigDecimal value;

    private Boolean active = true;

    private LocalDateTime startsAt;

    private LocalDateTime endsAt;

    @Min(value = 0, message = "Max uses must be 0 or greater")
    private Integer maxUses;

    @Min(value = 0, message = "Used count must be 0 or greater")
    private Integer usedCount = 0;
}

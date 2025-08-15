package com.velsystems.ecommerce.model;

import com.velsystems.ecommerce.enums.CouponType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false, length = 64)
    private String code;

    @Column(nullable = false, length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CouponType type;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal value;

    private Boolean active = true;

    private LocalDateTime startsAt;

    private LocalDateTime endsAt;

    private Integer maxUses;

    @Column(nullable = false)
    private Integer usedCount = 0;
}

package com.velsystems.ecommerce.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Coupon Type: 0=PERCENTAGE, 1=FIXED_AMOUNT, 2=FREE_SHIPPING")
public enum CouponType {
    PERCENTAGE(0),
    FIXED_AMOUNT(1),
    FREE_SHIPPING(2);

    private final int code;

    CouponType(int code) {
        this.code = code;
    }

    @JsonValue
    public int getCode() {
        return code;
    }

    @JsonCreator
    public static CouponType fromCode(int code) {
        for (CouponType type : values()) {
            if (type.code == code) return type;
        }
        throw new IllegalArgumentException("Invalid type code: " + code);
    }
}
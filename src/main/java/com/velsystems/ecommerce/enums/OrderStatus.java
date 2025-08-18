package com.velsystems.ecommerce.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Order status: 0=PENDING, 1=PAID, 2=FULFILLED, 3=CANCELLED, 4=REFUNDED")
public enum OrderStatus {
    PENDING(0),
    PAID(1),
    FULFILLED(2),
    CANCELLED(3),
    REFUNDED(4);
    private final int code;

    OrderStatus(int code) {
        this.code = code;
    }

    @JsonValue
    public int getCode() {
        return code;
    }

    @JsonCreator
    public static OrderStatus fromCode(int code) {
        for (OrderStatus type : values()) {
            if (type.code == code) return type;
        }
        throw new IllegalArgumentException("Invalid status code: " + code);
    }
}
package com.velsystems.ecommerce.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Payment status: 0=INITIATED, 1=AUTHORIZED, 2=CAPTURED, 3=FAILED, 4=REFUNDED, 5=PENDING, 6=SUCCESS")
public enum PaymentStatus {
    INITIATED(0),
    AUTHORIZED(1),
    CAPTURED(2),
    FAILED(3),
    REFUNDED(4),
    PENDING(5),
    SUCCESS(6);

    private final int code;

    PaymentStatus(int code) {
        this.code = code;
    }

    @JsonValue
    public int getCode() {
        return code;
    }

    @JsonCreator
    public static PaymentStatus fromCode(int code) {
        for (PaymentStatus type : values()) {
            if (type.code == code) return type;
        }
        throw new IllegalArgumentException("Invalid status code: " + code);
    }
}
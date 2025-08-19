package com.velsystems.ecommerce.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Shipment status: 0=PENDING, 1=SHIPPED, 2=DELIVERED, 3=RETURNED")
public enum ShipmentStatus {
    PENDING(0),
    SHIPPED(1),
    DELIVERED(2),
    RETURNED(3);
    private final int code;

    ShipmentStatus(int code) {
        this.code = code;
    }

    @JsonValue
    public int getCode() {
        return code;
    }

    @JsonCreator
    public static ShipmentStatus fromCode(int code) {
        for (ShipmentStatus type : values()) {
            if (type.code == code) return type;
        }
        throw new IllegalArgumentException("Invalid status code: " + code);
    }
}
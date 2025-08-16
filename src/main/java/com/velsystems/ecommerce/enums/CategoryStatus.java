package com.velsystems.ecommerce.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Category Status: 0=ACTIVE, 1=INACTIVE")
public enum CategoryStatus {
    ACTIVE(0),
    INACTIVE(1);
    private final int code;

    CategoryStatus(int code) {
        this.code = code;
    }

    @JsonValue
    public int getCode() {
        return code;
    }

    @JsonCreator
    public static CategoryStatus fromCode(int code) {
        for (CategoryStatus type : values()) {
            if (type.code == code) return type;
        }
        throw new IllegalArgumentException("Invalid type code: " + code);
    }
}

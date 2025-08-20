package com.velsystems.ecommerce.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Address Type: 0=HOME, 1=WORK")
public enum AddressType {
    HOME(0),
    WORK(1);
    private final int code;

    AddressType(int code) {
        this.code = code;
    }

    @JsonValue
    public int getCode() {
        return code;
    }

    @JsonCreator
    public static AddressType fromCode(int code) {
        for (AddressType type : values()) {
            if (type.code == code) return type;
        }
        throw new IllegalArgumentException("Invalid type code: " + code);
    }
}

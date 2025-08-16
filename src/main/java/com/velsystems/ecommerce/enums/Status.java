package com.velsystems.ecommerce.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status: 0=ACTIVE, 1=INACTIVE, 2=BANNED")
public enum Status {
    ACTIVE(0),
    INACTIVE(1),
    BANNED(2);
    private final int code;

    Status(int code) {
        this.code = code;
    }

    @JsonValue
    public int getCode() {
        return code;
    }

    @JsonCreator
    public static Status fromCode(int code) {
        for (Status status: values()) {
            if (status.code == code) return status;
        }
        throw new IllegalArgumentException("Invalid status code: " + code);
    }
}
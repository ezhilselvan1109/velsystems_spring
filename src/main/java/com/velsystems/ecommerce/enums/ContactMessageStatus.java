package com.velsystems.ecommerce.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Contact Message Status: 0=NEW, 1=IN_PROGRESS, 2=RESOLVED")
public enum ContactMessageStatus {
    NEW(0),
    IN_PROGRESS(1),
    RESOLVED(2);
    private final int code;

    ContactMessageStatus(int code) {
        this.code = code;
    }

    @JsonValue
    public int getCode() {
        return code;
    }

    @JsonCreator
    public static ContactMessageStatus fromCode(int code) {
        for (ContactMessageStatus type : values()) {
            if (type.code == code) return type;
        }
        throw new IllegalArgumentException("Invalid status code: " + code);
    }
}
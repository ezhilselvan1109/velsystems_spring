package com.velsystems.ecommerce.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

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
        for (Status status : values()) {
            if (status.code == code) return status;
        }
        throw new IllegalArgumentException("Invalid status code: " + code);
    }
}

/**
 * Spring Converter to handle query parameters like ?status=1 or ?status=INACTIVE
 */
@Component
class StringToStatusConverter implements Converter<String, Status> {

    @Override
    public Status convert(String source) {
        try {
            // If user passes 0,1,2
            int code = Integer.parseInt(source);
            return Status.fromCode(code);
        } catch (NumberFormatException e) {
            // If user passes ACTIVE, INACTIVE, BANNED
            return Status.valueOf(source.toUpperCase());
        }
    }
}

package com.velsystems.ecommerce.dto.response.contact;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactStatusStatsDto {
    private String status;
    private long count;
}

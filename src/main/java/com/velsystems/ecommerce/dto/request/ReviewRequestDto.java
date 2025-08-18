package com.velsystems.ecommerce.dto.request;

import lombok.*;

import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReviewRequestDto {
    private UUID productId;
    private Integer rating;   // 1–5
    private String comment;
}
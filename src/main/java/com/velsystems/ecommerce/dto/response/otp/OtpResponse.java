package com.velsystems.ecommerce.dto.response.otp;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class OtpResponse {
    private String requestId;
    private String email;
    private LocalDateTime expiryDate;
    private String tooltipText;
}

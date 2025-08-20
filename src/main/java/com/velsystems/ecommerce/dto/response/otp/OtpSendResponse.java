package com.velsystems.ecommerce.dto.response.otp;

import lombok.*;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class OtpSendResponse {
    private List<OtpResponse> otpIdentifierInfo;
    private String toastMessage;
}

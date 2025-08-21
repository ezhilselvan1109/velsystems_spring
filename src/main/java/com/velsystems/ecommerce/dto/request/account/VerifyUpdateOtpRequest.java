package com.velsystems.ecommerce.dto.request.account;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VerifyUpdateOtpRequest {
    private String identifier; // email/phone
    private String otp;
    private String requestId;
}

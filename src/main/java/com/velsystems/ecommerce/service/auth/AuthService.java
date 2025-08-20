package com.velsystems.ecommerce.service.auth;

import com.velsystems.ecommerce.security.Util;
import com.velsystems.ecommerce.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AuthService implements IAuthService{
    private final EmailService emailService;
    private final Util util;
    public void verifyOtp(String otp) {

    }

    public void requestOtp(String identifier) {

    }

}

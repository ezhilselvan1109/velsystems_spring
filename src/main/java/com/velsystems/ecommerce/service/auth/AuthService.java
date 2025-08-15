package com.velsystems.ecommerce.service.auth;

import com.velsystems.ecommerce.model.OtpCode;
import com.velsystems.ecommerce.model.User;
import com.velsystems.ecommerce.repository.OtpRepository;
import com.velsystems.ecommerce.security.Util;
import com.velsystems.ecommerce.service.EmailService;
import com.velsystems.ecommerce.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AuthService implements IAuthService{
    private final OtpRepository otpRepository;
    private final IUserService userService;
    private final EmailService emailService;
    private final Util util;
    public void verifyOtp(String otp) {
        UUID userId=util.getAuthenticatedUserId();
        User user=User.builder().id(userId).build();
        OtpCode otpCode = otpRepository
                .findByUserAndOtpCodeAndIsUsedFalseAndExpiresAtAfter(user, otp, LocalDateTime.now())
                .orElseThrow(() -> new RuntimeException("Invalid or expired OTP"));
        otpCode.setIsUsed(true);
        otpRepository.save(otpCode);
    }

    public void requestOtp(String identifier) {
        User user=userService.findByEmailOrPhoneNumber(identifier);
        String otp = String.format("%06d", new Random().nextInt(999999));

        OtpCode otpCode = OtpCode.builder()
                .user(user)
                .otpCode(otp)
                .otpType(identifier.contains("@") ? "EMAIL" : "PHONE")
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .isUsed(false)
                .build();

        otpRepository.save(otpCode);

        if (otpCode.getOtpType().equals("EMAIL")) {
            emailService.sendOtp(identifier, otp);
        } else {
            /*smsService.sendOtp(user.getPhoneNumber(), otp); // Implement SMS sending*/
        }
    }

}

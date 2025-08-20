package com.velsystems.ecommerce.service;

import com.velsystems.ecommerce.dto.response.otp.OtpResponse;
import com.velsystems.ecommerce.model.OtpToken;
import com.velsystems.ecommerce.repository.OtpTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpTokenRepository otpRepo;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public OtpResponse generateAndSendOtp(String email) {
        otpRepo.deleteByEmail(email);

        String rawOtp = String.format("%06d", new Random().nextInt(999999));
        String hashedOtp = passwordEncoder.encode(rawOtp);
        OtpToken token = OtpToken.builder()
                .email(email)
                .otp(hashedOtp)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .build();
        OtpToken tokens= otpRepo.save(token);

        emailService.sendOtp(email, rawOtp);

        return OtpResponse.builder()
                .requestId(tokens.getId())
                .email(email)
                .expiryDate(token.getExpiryTime())
                .tooltipText("Enter OTP sent to " + email)
                .build();
    }

    @Transactional
    public boolean validateOtp(String email, String otp, String requestId) {
        return otpRepo.findByEmailAndId(email, requestId)
                .map(token -> {
                    if (token.getExpiryTime().isBefore(LocalDateTime.now())) {
                        return false;
                    }

                    if (passwordEncoder.matches(otp, token.getOtp())) {
                        otpRepo.delete(token);
                        return true;
                    } else {
                        otpRepo.save(token);
                        return false;
                    }
                })
                .orElse(false);
    }
}
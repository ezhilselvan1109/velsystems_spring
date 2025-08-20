package com.velsystems.ecommerce.service;

import com.velsystems.ecommerce.model.OtpToken;
import com.velsystems.ecommerce.repository.OtpTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpTokenRepository otpRepo;
    private final EmailService emailService;

    @Transactional
    public void generateAndSendOtp(String email) {
        otpRepo.deleteByEmail(email);

        String otp = String.format("%06d", new Random().nextInt(999999));
        OtpToken token = OtpToken.builder()
                .email(email)
                .otp(otp)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .build();
        otpRepo.save(token);

        emailService.sendOtp(email, otp);
    }

    public boolean validateOtp(String email, String otp) {
        return otpRepo.findByEmailAndOtp(email, otp)
                .filter(t -> t.getExpiryTime().isAfter(LocalDateTime.now()))
                .isPresent();
    }
}
package com.velsystems.ecommerce.service.user;

import com.velsystems.ecommerce.dto.request.user.*;
import com.velsystems.ecommerce.dto.response.UserResponse;
import com.velsystems.ecommerce.dto.response.otp.OtpResponse;
import com.velsystems.ecommerce.dto.response.otp.OtpSendResponse;
import com.velsystems.ecommerce.model.User;
import com.velsystems.ecommerce.repository.UserRepository;
import com.velsystems.ecommerce.security.Util;
import com.velsystems.ecommerce.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OtpService otpService;
    private final Util util;

    @Override
    public User getUser(){
        UUID userId=util.getAuthenticatedUserId();
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    @Override
    public UserResponse updatePersonalInfo(UpdatePersonalInfoRequest request) {
        User user = getUser();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGender(request.getGender());
        userRepository.save(user);

        return toResponse(user);
    }

    @Override
    public OtpSendResponse requestEmailUpdateOtp(UpdateEmailRequest request) {
        OtpResponse otpResponse = otpService.generateAndSendOtp(request.getNewEmail());

        return OtpSendResponse.builder()
                .otpIdentifierInfo(List.of(otpResponse))
                .toastMessage("OTP sent to " + request.getNewEmail())
                .build();
    }

    @Override
    public UserResponse verifyEmailUpdateOtp(VerifyUpdateOtpRequest request) {
        if (!otpService.validateOtp(request.getIdentifier(), request.getOtp(), request.getRequestId())) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        User user = getUser();
        user.setEmail(request.getIdentifier());
        userRepository.save(user);

        return toResponse(user);
    }

    @Override
    public OtpSendResponse requestPhoneUpdateOtp(UpdatePhoneRequest request) {
        OtpResponse otpResponse = otpService.generateAndSendOtp(request.getNewPhoneNumber());

        return OtpSendResponse.builder()
                .otpIdentifierInfo(List.of(otpResponse))
                .toastMessage("OTP sent to " + request.getNewPhoneNumber())
                .build();
    }

    @Override
    public UserResponse verifyPhoneUpdateOtp(VerifyUpdateOtpRequest request) {
        if (!otpService.validateOtp(request.getIdentifier(), request.getOtp(), request.getRequestId())) {
            throw new RuntimeException("Invalid or expired OTP");
        }
        User user = getUser();
        user.setPhoneNumber(request.getIdentifier());
        userRepository.save(user);

        return toResponse(user);
    }

    private UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}

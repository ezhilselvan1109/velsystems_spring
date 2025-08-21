package com.velsystems.ecommerce.service.auth;

import com.velsystems.ecommerce.dto.request.auth.SendOtpRequest;
import com.velsystems.ecommerce.dto.request.auth.VerifyOtpRequest;
import com.velsystems.ecommerce.dto.response.UserResponse;
import com.velsystems.ecommerce.dto.response.otp.OtpResponse;
import com.velsystems.ecommerce.dto.response.otp.OtpSendResponse;
import com.velsystems.ecommerce.enums.Role;
import com.velsystems.ecommerce.enums.Status;
import com.velsystems.ecommerce.model.User;
import com.velsystems.ecommerce.repository.UserRepository;
import com.velsystems.ecommerce.response.ApiResponse;
import com.velsystems.ecommerce.security.CookieUtil;
import com.velsystems.ecommerce.security.JwtUtil;
import com.velsystems.ecommerce.security.Util;
import com.velsystems.ecommerce.service.OtpService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final JwtUtil jwtUtil;
    private final OtpService otpService;
    private final UserRepository userRepository;
    private final Util util;
    private final boolean PROD = false;

    @Override
    public ApiResponse sendOtp(SendOtpRequest request) {
        OtpResponse otpResponse = otpService.generateAndSendOtp(request.getEmail());

        OtpSendResponse response = OtpSendResponse.builder()
                .otpIdentifierInfo(List.of(otpResponse))
                .toastMessage("OTP successfully sent to " + request.getEmail())
                .build();

        return new ApiResponse("success", response);
    }

    @Override
    public ApiResponse verifyOtp(VerifyOtpRequest request, HttpServletResponse response, boolean isAdmin) {
        if (otpService.validateOtp(request.getEmail(), request.getOtp(), request.getRequestId())) {
            User user = userRepository.findByEmail(request.getEmail()).orElse(null);

            if (user == null) {
                user = User.builder()
                        .email(request.getEmail())
                        .role(isAdmin ? Role.ADMIN : Role.USER)
                        .status(Status.ACTIVE)
                        .build();
                userRepository.save(user);
            }

            Role primaryRole = user.getRole();

            UserResponse userResponse = UserResponse.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .build();

            String token = jwtUtil.generateToken(primaryRole, userResponse);

            Cookie cookie = CookieUtil.buildAuthCookie(token, PROD);
            response.addCookie(cookie);

            return new ApiResponse("success", "OTP verified, user authenticated");
        }
        return new ApiResponse("error", "Invalid or expired OTP");
    }

    @Override
    public ApiResponse logout(HttpServletResponse response) {
        response.addCookie(CookieUtil.clearAuthCookie(PROD));
        return new ApiResponse("success", null);
    }

    @Override
    public ApiResponse getMe() {
        return new ApiResponse("success", util.getAuthenticatedUser());
    }
}

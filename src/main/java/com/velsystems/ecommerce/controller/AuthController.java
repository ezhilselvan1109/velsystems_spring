package com.velsystems.ecommerce.controller;

import com.velsystems.ecommerce.dto.request.auth.SendOtpRequest;
import com.velsystems.ecommerce.dto.request.auth.VerifyOtpRequest;
import com.velsystems.ecommerce.dto.response.UserResponse;
import com.velsystems.ecommerce.enums.Role;
import com.velsystems.ecommerce.enums.Status;
import com.velsystems.ecommerce.model.User;
import com.velsystems.ecommerce.repository.UserRepository;
import com.velsystems.ecommerce.response.ApiResponse;
import com.velsystems.ecommerce.security.CookieUtil;
import com.velsystems.ecommerce.security.JwtUtil;
import com.velsystems.ecommerce.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final OtpService otpService;
    private final UserRepository userRepository;
    private final boolean PROD = false;

    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse> sendOtp(@RequestBody SendOtpRequest request) {
        otpService.generateAndSendOtp(request.getEmail());
        return ResponseEntity.ok(new ApiResponse("success", "OTP sent to email"));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse> verifyOtp(@RequestBody VerifyOtpRequest request,
                                                 HttpServletResponse response) {
        if (otpService.validateOtp(request.getEmail(), request.getOtp())) {
            User user = userRepository.findByEmail(request.getEmail()).orElse(null);

            if (user == null) {
                user = User.builder()
                        .email(request.getEmail())
                        .roles(Set.of(Role.USER))
                        .status(Status.ACTIVE)
                        .build();
                userRepository.save(user);
            }

            Role primaryRole = user.getRoles().stream().findFirst().orElse(Role.USER);

            UserResponse userResponse = UserResponse.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .build();

            String token = jwtUtil.generateToken(primaryRole,userResponse);

            Cookie cookie = CookieUtil.buildAuthCookie(token, PROD);
            response.addCookie(cookie);

            return ResponseEntity.ok(new ApiResponse("success", "OTP verified, user authenticated"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("error", "Invalid or expired OTP"));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpServletResponse response) {
        response.addCookie(CookieUtil.clearAuthCookie(PROD));
        return ResponseEntity.ok(new ApiResponse("successful",null));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getMe() {
        return null;
    }
}
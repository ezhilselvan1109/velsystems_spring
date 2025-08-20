package com.velsystems.ecommerce.controller;

import com.velsystems.ecommerce.dto.request.user.UpdateEmailRequest;
import com.velsystems.ecommerce.dto.request.user.UpdatePersonalInfoRequest;
import com.velsystems.ecommerce.dto.request.user.UpdatePhoneRequest;
import com.velsystems.ecommerce.dto.request.user.VerifyUpdateOtpRequest;
import com.velsystems.ecommerce.dto.response.UserResponse;
import com.velsystems.ecommerce.dto.response.otp.OtpSendResponse;
import com.velsystems.ecommerce.response.ApiResponse;
import com.velsystems.ecommerce.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/update-info")
    public ResponseEntity<ApiResponse> updatePersonalInfo(
            @RequestBody UpdatePersonalInfoRequest request) {
        UserResponse user = userService.updatePersonalInfo(request);
        return ResponseEntity.ok(new ApiResponse("success", user));
    }

    @PostMapping("/update-email/request-otp")
    public ResponseEntity<ApiResponse> requestEmailUpdateOtp(
            @RequestBody UpdateEmailRequest request) {
        OtpSendResponse response = userService.requestEmailUpdateOtp(request);
        return ResponseEntity.ok(new ApiResponse("success", response));
    }

    @PostMapping("/update-email/verify-otp")
    public ResponseEntity<ApiResponse> verifyEmailUpdateOtp(
            @RequestBody VerifyUpdateOtpRequest request) {
        UserResponse user = userService.verifyEmailUpdateOtp(request);
        return ResponseEntity.ok(new ApiResponse("success", user));
    }

    @PostMapping("/update-phone/request-otp")
    public ResponseEntity<ApiResponse> requestPhoneUpdateOtp(
            @RequestBody UpdatePhoneRequest request) {
        OtpSendResponse response = userService.requestPhoneUpdateOtp(request);
        return ResponseEntity.ok(new ApiResponse("success", response));
    }

    @PostMapping("/update-phone/verify-otp")
    public ResponseEntity<ApiResponse> verifyPhoneUpdateOtp(
            @RequestBody VerifyUpdateOtpRequest request) {
        UserResponse user = userService.verifyPhoneUpdateOtp(request);
        return ResponseEntity.ok(new ApiResponse("success", user));
    }
}

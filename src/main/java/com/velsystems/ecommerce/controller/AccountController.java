package com.velsystems.ecommerce.controller;

import com.velsystems.ecommerce.dto.request.account.UpdateEmailRequest;
import com.velsystems.ecommerce.dto.request.account.UpdatePersonalInfoRequest;
import com.velsystems.ecommerce.dto.request.account.UpdatePhoneRequest;
import com.velsystems.ecommerce.dto.request.account.VerifyUpdateOtpRequest;
import com.velsystems.ecommerce.dto.response.AccountResponse;
import com.velsystems.ecommerce.dto.response.otp.OtpSendResponse;
import com.velsystems.ecommerce.response.ApiResponse;
import com.velsystems.ecommerce.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PutMapping("/update-info")
    public ResponseEntity<ApiResponse> updatePersonalInfo(
            @RequestBody UpdatePersonalInfoRequest request) {
        AccountResponse account = accountService.updatePersonalInfo(request);
        return ResponseEntity.ok(new ApiResponse("success", account));
    }

    @PostMapping("/update-email/request-otp")
    public ResponseEntity<ApiResponse> requestEmailUpdateOtp(
            @RequestBody UpdateEmailRequest request) {
        OtpSendResponse response = accountService.requestEmailUpdateOtp(request);
        return ResponseEntity.ok(new ApiResponse("success", response));
    }

    @PostMapping("/update-email/verify-otp")
    public ResponseEntity<ApiResponse> verifyEmailUpdateOtp(
            @RequestBody VerifyUpdateOtpRequest request) {
        AccountResponse account = accountService.verifyEmailUpdateOtp(request);
        return ResponseEntity.ok(new ApiResponse("success", account));
    }

    @PostMapping("/update-phone/request-otp")
    public ResponseEntity<ApiResponse> requestPhoneUpdateOtp(
            @RequestBody UpdatePhoneRequest request) {
        OtpSendResponse response = accountService.requestPhoneUpdateOtp(request);
        return ResponseEntity.ok(new ApiResponse("success", response));
    }

    @PostMapping("/update-phone/verify-otp")
    public ResponseEntity<ApiResponse> verifyPhoneUpdateOtp(
            @RequestBody VerifyUpdateOtpRequest request) {
        AccountResponse account = accountService.verifyPhoneUpdateOtp(request);
        return ResponseEntity.ok(new ApiResponse("success", account));
    }
}

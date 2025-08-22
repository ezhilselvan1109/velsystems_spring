package com.velsystems.ecommerce.controller;

import com.velsystems.ecommerce.dto.request.account.UpdateEmailRequest;
import com.velsystems.ecommerce.dto.request.account.UpdatePersonalInfoRequest;
import com.velsystems.ecommerce.dto.request.account.UpdatePhoneRequest;
import com.velsystems.ecommerce.dto.request.account.VerifyUpdateOtpRequest;
import com.velsystems.ecommerce.dto.response.AccountResponse;
import com.velsystems.ecommerce.dto.response.account.AccountInfoResponse;
import com.velsystems.ecommerce.dto.response.otp.OtpSendResponse;
import com.velsystems.ecommerce.response.ApiResponse;
import com.velsystems.ecommerce.service.account.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Account", description = "Account management APIs")
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "Update personal information",
            description = "Allows the authenticated user to update their personal information (first name, last name, gender).")
    @PutMapping("/info")
    public ResponseEntity<ApiResponse> updatePersonalInfo(
            @RequestBody UpdatePersonalInfoRequest request) {
        AccountResponse account = accountService.updatePersonalInfo(request);
        return ResponseEntity.ok(new ApiResponse("success", account));
    }

    @Operation(summary = "get account information",
            description = "Allows the authenticated user to get their account information.")
    @GetMapping
    public ResponseEntity<ApiResponse> getAccountInfo() {
        AccountInfoResponse account = accountService.getAccountInfo();
        return ResponseEntity.ok(new ApiResponse("success", account));
    }

    @Operation(summary = "Request OTP for email update",
            description = "Sends an OTP to the new email address for verification before updating.")
    @PostMapping("/email/request-otp")
    public ResponseEntity<ApiResponse> requestEmailUpdateOtp(
            @RequestBody UpdateEmailRequest request) {
        OtpSendResponse response = accountService.requestEmailUpdateOtp(request);
        return ResponseEntity.ok(new ApiResponse("success", response));
    }

    @Operation(summary = "Verify OTP for email update",
            description = "Verifies OTP and updates the email if the OTP is valid.")
    @PostMapping("/email/verify-otp")
    public ResponseEntity<ApiResponse> verifyEmailUpdateOtp(
            @RequestBody VerifyUpdateOtpRequest request) {
        AccountResponse account = accountService.verifyEmailUpdateOtp(request);
        return ResponseEntity.ok(new ApiResponse("success", account));
    }

    @Operation(summary = "Request OTP for phone update",
            description = "Sends an OTP to the new phone number for verification before updating.")
    @PostMapping("/phone/request-otp")
    public ResponseEntity<ApiResponse> requestPhoneUpdateOtp(
            @RequestBody UpdatePhoneRequest request) {
        OtpSendResponse response = accountService.requestPhoneUpdateOtp(request);
        return ResponseEntity.ok(new ApiResponse("success", response));
    }
    @Operation(summary = "Verify OTP for phone update",
            description = "Verifies OTP and updates the phone number if the OTP is valid.")
    @PostMapping("/phone/verify-otp")
    public ResponseEntity<ApiResponse> verifyPhoneUpdateOtp(
            @RequestBody VerifyUpdateOtpRequest request) {
        AccountResponse account = accountService.verifyPhoneUpdateOtp(request);
        return ResponseEntity.ok(new ApiResponse("success", account));
    }
}

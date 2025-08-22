package com.velsystems.ecommerce.controller;

import com.velsystems.ecommerce.dto.request.auth.SendOtpRequest;
import com.velsystems.ecommerce.dto.request.auth.VerifyOtpRequest;
import com.velsystems.ecommerce.response.api.ApiResponse;
import com.velsystems.ecommerce.service.auth.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth management APIs")
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/otp/generate")
    @Operation(
            summary = "Generate and send OTP",
            description = "Generates an OTP and sends it to the given email address."
    )
    public ResponseEntity<ApiResponse<?>> sendOtp(@RequestBody SendOtpRequest request) {
        return ResponseEntity.ok(ApiResponse.success("OTP sent successfully", authService.sendOtp(request)));
    }

    @PostMapping("/user/sign-in/verify-otp")
    @Operation(
            summary = "Verify OTP for User",
            description = "Verifies OTP for a USER role and authenticates the user."
    )
    public ResponseEntity<ApiResponse<?>> userSignInVerifyOtp(@RequestBody VerifyOtpRequest request,
                                                        HttpServletResponse response) {
        return ResponseEntity.ok(ApiResponse.success("OTP verified, user authenticated",
                authService.verifyOtp(request, response, false)));
    }

    @PostMapping("/user/sign-up/verify-otp")
    @Operation(
            summary = "Verify OTP for User",
            description = "Verifies OTP for a USER role and authenticates the user."
    )
    public ResponseEntity<ApiResponse<?>> userSignUpVerifyOtp(@RequestBody VerifyOtpRequest request,
                                                        HttpServletResponse response) {
        return ResponseEntity.ok(ApiResponse.success("OTP verified, user authenticated",
                authService.verifyOtp(request, response, false)));
    }

    @PostMapping("/admin/sign-in/verify-otp")
    @Operation(
            summary = "Verify OTP for Admin",
            description = "Verifies OTP for an ADMIN role and authenticates the user."
    )
    public ResponseEntity<ApiResponse<?>> adminSignInVerifyOtp(@RequestBody VerifyOtpRequest request,
                                                         HttpServletResponse response) {
        return ResponseEntity.ok(ApiResponse.success("OTP verified, admin authenticated",
                authService.verifyOtp(request, response, true)));
    }

    @PostMapping("/admin/sign-up/verify-otp")
    @Operation(
            summary = "Verify OTP for Admin",
            description = "Verifies OTP for an ADMIN role and authenticates the user."
    )
    public ResponseEntity<ApiResponse<?>> adminSignUpVerifyOtp(@RequestBody VerifyOtpRequest request,
                                                         HttpServletResponse response) {
        return ResponseEntity.ok(ApiResponse.success("OTP verified, admin authenticated",
                authService.verifyOtp(request, response, true)));
    }

    @PostMapping("/logout")
    @Operation(
            summary = "Logout",
            description = "Clears authentication cookie and logs out the current user."
    )
    public ResponseEntity<ApiResponse<?>> logout(HttpServletResponse response) {
        return ResponseEntity.ok(ApiResponse.success("Logout successful", authService.logout(response)));
    }

    @GetMapping("/me")
    @Operation(
            summary = "Get Current User",
            description = "Fetches details of the currently authenticated user."
    )
    public ResponseEntity<ApiResponse<?>> getMe() {
        return ResponseEntity.ok(ApiResponse.success("Current user details", authService.getMe()));
    }
}
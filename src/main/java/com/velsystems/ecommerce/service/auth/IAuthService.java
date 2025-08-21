package com.velsystems.ecommerce.service.auth;

import com.velsystems.ecommerce.dto.request.auth.SendOtpRequest;
import com.velsystems.ecommerce.dto.request.auth.VerifyOtpRequest;
import com.velsystems.ecommerce.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthService {
    ApiResponse sendOtp(SendOtpRequest request);

    ApiResponse verifyOtp(VerifyOtpRequest request, HttpServletResponse response, boolean isAdmin);

    ApiResponse logout(HttpServletResponse response);

    ApiResponse getMe();
}

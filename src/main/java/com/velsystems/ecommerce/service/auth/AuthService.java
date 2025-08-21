package com.velsystems.ecommerce.service.auth;

import com.velsystems.ecommerce.dto.request.auth.SendOtpRequest;
import com.velsystems.ecommerce.dto.request.auth.VerifyOtpRequest;
import com.velsystems.ecommerce.dto.response.AccountResponse;
import com.velsystems.ecommerce.dto.response.otp.OtpResponse;
import com.velsystems.ecommerce.dto.response.otp.OtpSendResponse;
import com.velsystems.ecommerce.enums.Role;
import com.velsystems.ecommerce.enums.Status;
import com.velsystems.ecommerce.model.Account;
import com.velsystems.ecommerce.repository.AccountRepository;
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
    private final AccountRepository accountRepository;
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
            Account account = accountRepository.findByEmail(request.getEmail()).orElse(null);

            if (account == null) {
                account = Account.builder()
                        .email(request.getEmail())
                        .role(isAdmin ? Role.ADMIN : Role.USER)
                        .status(Status.ACTIVE)
                        .build();
                accountRepository.save(account);
            }

            Role primaryRole = account.getRole();

            AccountResponse accountResponse = AccountResponse.builder()
                    .id(account.getId())
                    .email(account.getEmail())
                    .phoneNumber(account.getPhoneNumber())
                    .build();

            String token = jwtUtil.generateToken(primaryRole, accountResponse);

            Cookie cookie = CookieUtil.buildAuthCookie(token, PROD);
            response.addCookie(cookie);

            return new ApiResponse("success", "OTP verified, account authenticated");
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
        return new ApiResponse("success", util.getAuthenticatedAccount());
    }
}

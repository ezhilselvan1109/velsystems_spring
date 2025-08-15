package com.velsystems.ecommerce.controller;

import com.velsystems.ecommerce.dto.response.UserResponse;
import com.velsystems.ecommerce.enums.Role;
import com.velsystems.ecommerce.response.ApiResponse;
import com.velsystems.ecommerce.security.CookieUtil;
import com.velsystems.ecommerce.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;

    private final boolean PROD = false; // flip to true in prod to force Secure cookies

    @PostMapping("/register")
    public ResponseEntity<?> register() {

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login( HttpServletResponse response) {

        UserResponse userResponse= UserResponse.builder().email("").phoneNumber("").role(Role.ADMIN).build();

        String jwt = jwtUtil.generateToken(userResponse);

        Cookie cookie = CookieUtil.buildAuthCookie(jwt, PROD);
        // Add SameSite=None or Lax if you need cross-site. Example for None:
        // response.setHeader("Set-Cookie", String.format("%s=%s; Max-Age=%d; Path=/; HttpOnly; Secure; SameSite=None", CookieUtil.AUTH_COOKIE, jwt, 3600));
        response.addCookie(cookie);

        return ResponseEntity.ok().body("success");
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpServletResponse response) {
        response.addCookie(CookieUtil.clearAuthCookie(PROD));
        return ResponseEntity.ok(new ApiResponse("successful",null));
    }

    @PostMapping("/request-otp")
    public ResponseEntity<ApiResponse> requestOtp(@RequestParam String identifier, HttpServletResponse response) {
        UserResponse userResponse= UserResponse.builder().email("ezhil@gmail.com").role(Role.ADMIN).build();
        String jwt = jwtUtil.generateToken(userResponse);
        Cookie cookie = CookieUtil.buildAuthCookie(jwt, PROD);
        response.addCookie(cookie);
        return ResponseEntity.ok(new ApiResponse("OTP sent successfully",null));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse> verifyOtp(@RequestParam String otp, HttpServletResponse response) {
        UserResponse userResponse= UserResponse.builder().email("ezhil@gmail.com").role(Role.ADMIN).build();
        String jwt = jwtUtil.generateToken(userResponse);
        Cookie cookie = CookieUtil.buildAuthCookie(jwt, PROD);
        response.addCookie(cookie);
        return ResponseEntity.ok(new ApiResponse("Login successful",null));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getMe() {
        return ResponseEntity.ok(new ApiResponse("successful",UserResponse.builder().email("ezhil@gmail.com").build()));
    }

}
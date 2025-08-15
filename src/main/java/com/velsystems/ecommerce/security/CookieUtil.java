package com.velsystems.ecommerce.security;

import jakarta.servlet.http.Cookie;

public class CookieUtil {
    public static final String AUTH_COOKIE = "AUTH-TOKEN";

    public static Cookie buildAuthCookie(String token, boolean prod) {
        Cookie cookie = new Cookie(AUTH_COOKIE, token);
        cookie.setHttpOnly(true); // not accessible to JS
        cookie.setSecure(prod);   // true when served over HTTPS (prod)
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60); // 1h, align with token TTL
        // SameSite is not directly on Cookie API; add via response header if needed
        return cookie;
    }

    public static Cookie clearAuthCookie(boolean prod) {
        Cookie cookie = new Cookie(AUTH_COOKIE, "");
        cookie.setHttpOnly(true);
        cookie.setSecure(prod);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        return cookie;
    }
}
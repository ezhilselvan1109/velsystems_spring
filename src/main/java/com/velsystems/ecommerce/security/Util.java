package com.velsystems.ecommerce.security;

import com.velsystems.ecommerce.model.User;
import com.velsystems.ecommerce.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Util {

    private final UserService userService;

    public UUID getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return null;
        }
        String email = authentication.getPrincipal().toString();
        User user = userService.findByEmailOrPhoneNumber(email);
        return user != null ? user.getId() : null;
    }
}

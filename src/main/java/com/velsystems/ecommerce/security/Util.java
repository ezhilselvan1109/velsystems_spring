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
        return null;
    }
}

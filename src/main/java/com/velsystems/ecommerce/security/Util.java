package com.velsystems.ecommerce.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Util {

    public UUID getAuthenticatedUserId() {
        return null;
    }
}

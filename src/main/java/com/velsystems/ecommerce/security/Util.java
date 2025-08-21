package com.velsystems.ecommerce.security;

import com.velsystems.ecommerce.model.Account;
import com.velsystems.ecommerce.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Util {

    private final AccountRepository accountRepository;

    public UUID getAuthenticatedAccountId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null; // or throw an Unauthorized exception
        }

        // Principal is stored as email string in JwtAuthFilter
        String email = (String) authentication.getPrincipal();

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        return account.getId(); // Assuming User has UUID id field
    }

    public Account getAuthenticatedAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null; // or throw an Unauthorized exception
        }

        // Principal is stored as email string in JwtAuthFilter
        String email = (String) authentication.getPrincipal();

        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}

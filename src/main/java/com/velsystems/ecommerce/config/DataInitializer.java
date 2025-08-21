package com.velsystems.ecommerce.config;

import com.velsystems.ecommerce.enums.Role;
import com.velsystems.ecommerce.model.Account;
import com.velsystems.ecommerce.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final AccountRepository accountRepo;
    private final PasswordEncoder encoder;

    /*@Bean
    CommandLineRunner seed() {
        return args -> {
            if (accountRepo.findByAccountname("admin").isEmpty()) {
                Account admin = Account.builder()
                        .accountname("admin")
                        .password(encoder.encode("admin123"))
                        .roles(Set.of(Role.ADMIN))
                        .build();

                accountRepo.save(admin);
            }
        };
    }*/
}

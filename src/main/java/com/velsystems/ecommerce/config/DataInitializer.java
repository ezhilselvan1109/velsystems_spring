package com.velsystems.ecommerce.config;

import com.velsystems.ecommerce.enums.Role;
import com.velsystems.ecommerce.model.User;
import com.velsystems.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    /*@Bean
    CommandLineRunner seed() {
        return args -> {
            if (userRepo.findByUsername("admin").isEmpty()) {
                User admin = User.builder()
                        .username("admin")
                        .password(encoder.encode("admin123"))
                        .roles(Set.of(Role.ADMIN))
                        .build();

                userRepo.save(admin);
            }
        };
    }*/
}

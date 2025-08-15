package com.velsystems.ecommerce.controller;

import com.velsystems.ecommerce.dto.RegisterRequest;
import com.velsystems.ecommerce.enums.Role;
import com.velsystems.ecommerce.model.User;
import com.velsystems.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Admin Dashboard Data";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-admin")
    public String createAdmin(@RequestBody RegisterRequest request) {
        User admin = new User();
        admin.setRoles(Set.of(Role.ADMIN));
        userRepo.save(admin);
        return "Admin created successfully";
    }
}

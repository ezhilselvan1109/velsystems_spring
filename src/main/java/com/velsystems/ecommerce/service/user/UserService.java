package com.velsystems.ecommerce.service.user;

import com.velsystems.ecommerce.model.User;
import com.velsystems.ecommerce.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepo;
    public User findByEmailOrPhoneNumber(String identifier) {
        return userRepo.findByEmail(identifier)
                .or(() -> userRepo.findByPhoneNumber(identifier))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}

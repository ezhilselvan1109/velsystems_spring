package com.velsystems.ecommerce.dto;

import com.velsystems.ecommerce.enums.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private Set<Role> roles;
}



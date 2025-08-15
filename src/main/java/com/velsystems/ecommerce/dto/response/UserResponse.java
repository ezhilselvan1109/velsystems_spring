package com.velsystems.ecommerce.dto.response;

import com.velsystems.ecommerce.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String email;
    private String phoneNumber;
    private Role role;
}

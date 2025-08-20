package com.velsystems.ecommerce.dto.response;

import com.velsystems.ecommerce.enums.Role;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private UUID id;
    private String email;
    private String phoneNumber;
}

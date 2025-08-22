package com.velsystems.ecommerce.dto.response.account;

import lombok.*;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountInfoResponse {
    private UUID id;
    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String gender;
}

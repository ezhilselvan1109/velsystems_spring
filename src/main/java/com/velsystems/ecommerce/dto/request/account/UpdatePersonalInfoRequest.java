package com.velsystems.ecommerce.dto.request.account;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdatePersonalInfoRequest {
    private String firstName;
    private String lastName;
    private String gender;
}

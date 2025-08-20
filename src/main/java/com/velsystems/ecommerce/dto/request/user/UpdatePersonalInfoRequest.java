package com.velsystems.ecommerce.dto.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdatePersonalInfoRequest {
    private String firstName;
    private String lastName;
    private String gender;
}

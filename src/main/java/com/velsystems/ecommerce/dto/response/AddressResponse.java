package com.velsystems.ecommerce.dto.response;

import com.velsystems.ecommerce.enums.AddressType;
import lombok.Data;

import java.util.UUID;

@Data
public class AddressResponse {
    private UUID id;
    private String name;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String country;
    private String pinCode;
    private String landmark;
    private String alternatePhone;
    private AddressType addressType;
    private Boolean isDefault;
}

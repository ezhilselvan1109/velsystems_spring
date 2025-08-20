package com.velsystems.ecommerce.model;

import com.velsystems.ecommerce.enums.AddressType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
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
    private Boolean isDefault = false;
}

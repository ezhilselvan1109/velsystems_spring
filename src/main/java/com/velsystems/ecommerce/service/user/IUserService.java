package com.velsystems.ecommerce.service.user;

import com.velsystems.ecommerce.model.User;

public interface IUserService {
    User findByEmailOrPhoneNumber(String identifier);
}

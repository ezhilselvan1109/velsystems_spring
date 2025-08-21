package com.velsystems.ecommerce.repository;

import com.velsystems.ecommerce.model.Account;
import com.velsystems.ecommerce.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    List<Address> findByAccount(Account account);
}

package com.velsystems.ecommerce.repository;

import com.velsystems.ecommerce.model.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OtpTokenRepository extends JpaRepository<OtpToken, UUID> {

    Optional<OtpToken> findByEmailAndId(String email, String requestId);

    void deleteByEmail(String email);
}

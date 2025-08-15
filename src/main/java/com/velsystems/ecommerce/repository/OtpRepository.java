package com.velsystems.ecommerce.repository;

import com.velsystems.ecommerce.model.OtpCode;
import com.velsystems.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface OtpRepository extends JpaRepository<OtpCode, UUID> {
    Optional<OtpCode> findByUserAndOtpCodeAndIsUsedFalseAndExpiresAtAfter(User user, String otpCode, LocalDateTime now);
}

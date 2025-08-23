package com.velsystems.ecommerce.repository;

import com.velsystems.ecommerce.enums.ContactMessageStatus;
import com.velsystems.ecommerce.model.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, UUID> {
    long countByStatus(ContactMessageStatus status);
}

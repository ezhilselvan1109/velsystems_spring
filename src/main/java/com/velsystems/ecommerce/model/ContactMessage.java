package com.velsystems.ecommerce.model;

import com.velsystems.ecommerce.enums.ContactMessageStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "contact_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactMessage {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String email;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    private ContactMessageStatus status = ContactMessageStatus.NEW;

    private LocalDateTime createdAt = LocalDateTime.now();
}

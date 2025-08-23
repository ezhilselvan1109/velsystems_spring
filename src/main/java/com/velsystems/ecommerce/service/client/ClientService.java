package com.velsystems.ecommerce.service.client;

import com.velsystems.ecommerce.dto.request.contact.ContactRequestDto;
import com.velsystems.ecommerce.dto.response.contact.ContactResponseDto;
import com.velsystems.ecommerce.dto.response.contact.ContactStatusStatsDto;
import com.velsystems.ecommerce.enums.ContactMessageStatus;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ClientService {
    ContactResponseDto saveMessage(ContactRequestDto requestDto);
    Page<ContactResponseDto> getAllMessages(int page, int size);
    ContactResponseDto updateStatus(UUID id, ContactMessageStatus status);
    List<ContactStatusStatsDto> getStatusStats();
}

package com.velsystems.ecommerce.service.client;

import com.velsystems.ecommerce.dto.request.contact.ContactRequestDto;
import com.velsystems.ecommerce.dto.response.contact.ContactResponseDto;
import com.velsystems.ecommerce.dto.response.contact.ContactStatusStatsDto;
import com.velsystems.ecommerce.enums.ContactMessageStatus;
import com.velsystems.ecommerce.model.ContactMessage;
import com.velsystems.ecommerce.repository.ContactMessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {

    private final ContactMessageRepository repository;
    private final ModelMapper modelMapper;

    public ClientServiceImpl(ContactMessageRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ContactResponseDto saveMessage(ContactRequestDto requestDto) {
        ContactMessage message = modelMapper.map(requestDto, ContactMessage.class);
        ContactMessage saved = repository.save(message);
        return modelMapper.map(saved, ContactResponseDto.class);
    }

    @Override
    public Page<ContactResponseDto> getAllMessages(int page, int size) {
        return repository.findAll(PageRequest.of(page, size))
                .map(entity -> modelMapper.map(entity, ContactResponseDto.class));
    }

    @Override
    public ContactResponseDto updateStatus(UUID id, ContactMessageStatus status) {
        ContactMessage message = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        message.setStatus(status);

        ContactMessage updated = repository.save(message);
        return modelMapper.map(updated, ContactResponseDto.class);
    }

    @Override
    public List<ContactStatusStatsDto> getStatusStats() {
        return Arrays.stream(ContactMessageStatus.values())
                .map(status -> new ContactStatusStatsDto(
                        status.name(),
                        repository.countByStatus(status)
                ))
                .toList();
    }
}

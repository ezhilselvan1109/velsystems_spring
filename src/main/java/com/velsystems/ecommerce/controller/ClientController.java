package com.velsystems.ecommerce.controller;

import com.velsystems.ecommerce.dto.request.contact.ContactRequestDto;
import com.velsystems.ecommerce.dto.response.contact.ContactResponseDto;
import com.velsystems.ecommerce.dto.response.contact.ContactStatusStatsDto;
import com.velsystems.ecommerce.enums.ContactMessageStatus;
import com.velsystems.ecommerce.response.api.ApiResponse;
import com.velsystems.ecommerce.service.client.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/client")
@Tag(name = "Client", description = "APIs for Client Management")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @Operation(summary = "Save a new contact message",
            description = "Stores a customer contact message into the system")
    @PostMapping("/contact")
    public ApiResponse<ContactResponseDto> saveMessage(
            @Valid @RequestBody ContactRequestDto requestDto) {
        return ApiResponse.success("Message sent successfully", service.saveMessage(requestDto));
    }

    @Operation(summary = "Get all messages",
            description = "Fetches a paginated list of all contact messages")
    @GetMapping("/contact")
    public ApiResponse<Page<ContactResponseDto>> getAllMessages(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.success("Messages retrieved successfully", service.getAllMessages(page, size));
    }

    @Operation(summary = "Update message status",
            description = "Updates the status of a specific contact message. " +
                    "Valid codes: 0=NEW, 1=IN_PROGRESS, 2=RESOLVED")
    @PatchMapping("/contact/{id}/status")
    public ApiResponse<ContactResponseDto> updateStatus(
            @Parameter(description = "Message ID") @PathVariable UUID id,
            @Parameter(description = "Status code (0=NEW, 1=IN_PROGRESS, 2=RESOLVED)")
            @RequestParam Integer status) {
        return ApiResponse.success("Status updated successfully",
                service.updateStatus(id, ContactMessageStatus.fromCode(status)));
    }

    @Operation(summary = "Get status statistics",
            description = "Returns a count of messages by their status (NEW, IN_PROGRESS, RESOLVED)")
    @GetMapping("/stats")
    public ApiResponse<List<ContactStatusStatsDto>> getStatusStats() {
        return ApiResponse.success("Status stats retrieved successfully", service.getStatusStats());
    }
}

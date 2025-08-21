package com.velsystems.ecommerce.controller;

import com.velsystems.ecommerce.dto.request.AddressRequest;
import com.velsystems.ecommerce.dto.response.AddressResponse;
import com.velsystems.ecommerce.response.api.ApiResponse;
import com.velsystems.ecommerce.service.address.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
@Tag(name = "Address", description = "Account Addresses management APIs")
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "Get all addresses of the current account",
            description = "Fetches all saved addresses of the logged-in account")
    @GetMapping
    public ResponseEntity<ApiResponse<List<AddressResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Addresses fetched successfully", addressService.getAll()));
    }

    @Operation(summary = "Get address by ID", description = "Fetches a specific address by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AddressResponse>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Address fetched successfully", addressService.getById(id)));
    }

    @Operation(summary = "Add a new address", description = "Creates a new address for the current account")
    @PostMapping
    public ResponseEntity<ApiResponse<AddressResponse>> add(@RequestBody AddressRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Address added successfully", addressService.add(request)));
    }

    @Operation(summary = "Update an address", description = "Updates an existing address by ID")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AddressResponse>> update(@PathVariable UUID id, @RequestBody AddressRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Address updated successfully", addressService.update(id, request)));
    }

    @Operation(summary = "Delete an address", description = "Deletes an address by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        addressService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Address deleted successfully", null));
    }
}

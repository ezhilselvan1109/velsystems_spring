package com.velsystems.ecommerce.service.address;

import com.velsystems.ecommerce.dto.request.AddressRequest;
import com.velsystems.ecommerce.dto.response.AddressResponse;

import java.util.List;
import java.util.UUID;

public interface AddressService {
    AddressResponse add(AddressRequest request);
    List<AddressResponse> getAll();
    AddressResponse getById(UUID id);
    AddressResponse update(UUID id, AddressRequest request);
    void delete(UUID id);
}

package com.velsystems.ecommerce.service.address;

import com.velsystems.ecommerce.dto.request.AddressRequest;
import com.velsystems.ecommerce.dto.response.AddressResponse;
import com.velsystems.ecommerce.model.Address;
import com.velsystems.ecommerce.model.User;
import com.velsystems.ecommerce.repository.AddressRepository;
import com.velsystems.ecommerce.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Override
    public AddressResponse add(AddressRequest request) {
        User user = userService.getUser();
        Address address = modelMapper.map(request, Address.class);
        address.setUser(user);

        return modelMapper.map(addressRepository.save(address), AddressResponse.class);
    }

    @Override
    public List<AddressResponse> getAll() {
        User user = userService.getUser();
        return addressRepository.findByUser(user).stream()
                .map(addr -> modelMapper.map(addr, AddressResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public AddressResponse getById(UUID id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        return modelMapper.map(address, AddressResponse.class);
    }

    @Override
    public AddressResponse update(UUID id, AddressRequest request) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        modelMapper.map(request, address);
        return modelMapper.map(addressRepository.save(address), AddressResponse.class);
    }

    @Override
    public void delete(UUID id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        addressRepository.delete(address);
    }
}

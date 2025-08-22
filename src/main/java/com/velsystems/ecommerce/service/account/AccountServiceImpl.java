package com.velsystems.ecommerce.service.account;

import com.velsystems.ecommerce.dto.request.account.*;
import com.velsystems.ecommerce.dto.response.AccountResponse;
import com.velsystems.ecommerce.dto.response.account.AccountInfoResponse;
import com.velsystems.ecommerce.dto.response.otp.OtpResponse;
import com.velsystems.ecommerce.dto.response.otp.OtpSendResponse;
import com.velsystems.ecommerce.model.Account;
import com.velsystems.ecommerce.repository.AccountRepository;
import com.velsystems.ecommerce.security.Util;
import com.velsystems.ecommerce.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final OtpService otpService;
    private final Util util;
    private final ModelMapper modelMapper;

    @Override
    public Account getAccount(){
        UUID accountId=util.getAuthenticatedAccountId();
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @Override
    public AccountInfoResponse getAccountInfo() {
        Account account = getAccount();
        return modelMapper.map(account, AccountInfoResponse.class);
    }

    @Override
    public AccountResponse updatePersonalInfo(UpdatePersonalInfoRequest request) {
        Account account = getAccount();
        account.setFirstName(request.getFirstName());
        account.setLastName(request.getLastName());
        account.setGender(request.getGender());
        accountRepository.save(account);

        return toResponse(account);
    }

    @Override
    public OtpSendResponse requestEmailUpdateOtp(UpdateEmailRequest request) {
        OtpResponse otpResponse = otpService.generateAndSendOtp(request.getNewEmail());

        return OtpSendResponse.builder()
                .otpIdentifierInfo(List.of(otpResponse))
                .toastMessage("OTP sent to " + request.getNewEmail())
                .build();
    }

    @Override
    public AccountResponse verifyEmailUpdateOtp(VerifyUpdateOtpRequest request) {
        if (!otpService.validateOtp(request.getIdentifier(), request.getOtp(), request.getRequestId())) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        Account account = getAccount();
        account.setEmail(request.getIdentifier());
        accountRepository.save(account);

        return toResponse(account);
    }

    @Override
    public OtpSendResponse requestPhoneUpdateOtp(UpdatePhoneRequest request) {
        OtpResponse otpResponse = otpService.generateAndSendOtp(request.getNewPhoneNumber());

        return OtpSendResponse.builder()
                .otpIdentifierInfo(List.of(otpResponse))
                .toastMessage("OTP sent to " + request.getNewPhoneNumber())
                .build();
    }

    @Override
    public AccountResponse verifyPhoneUpdateOtp(VerifyUpdateOtpRequest request) {
        if (!otpService.validateOtp(request.getIdentifier(), request.getOtp(), request.getRequestId())) {
            throw new RuntimeException("Invalid or expired OTP");
        }
        Account account = getAccount();
        account.setPhoneNumber(request.getIdentifier());
        accountRepository.save(account);

        return toResponse(account);
    }

    @Override
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account not found with email: " + email));
    }

    private AccountResponse toResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .email(account.getEmail())
                .phoneNumber(account.getPhoneNumber())
                .build();
    }
}

package com.velsystems.ecommerce.service.account;

import com.velsystems.ecommerce.dto.request.account.UpdatePersonalInfoRequest;
import com.velsystems.ecommerce.dto.request.account.UpdateEmailRequest;
import com.velsystems.ecommerce.dto.request.account.UpdatePhoneRequest;
import com.velsystems.ecommerce.dto.request.account.VerifyUpdateOtpRequest;
import com.velsystems.ecommerce.dto.response.AccountResponse;
import com.velsystems.ecommerce.dto.response.account.AccountInfoResponse;
import com.velsystems.ecommerce.dto.response.otp.OtpSendResponse;
import com.velsystems.ecommerce.model.Account;

public interface AccountService {
    Account getAccount();
    AccountInfoResponse getAccountInfo();

    AccountResponse updatePersonalInfo(UpdatePersonalInfoRequest request);

    OtpSendResponse requestEmailUpdateOtp(UpdateEmailRequest request);

    AccountResponse verifyEmailUpdateOtp(VerifyUpdateOtpRequest request);

    OtpSendResponse requestPhoneUpdateOtp(UpdatePhoneRequest request);

    AccountResponse verifyPhoneUpdateOtp(VerifyUpdateOtpRequest request);

    Account findByEmail(String email);
}

package com.velsystems.ecommerce.service.user;

import com.velsystems.ecommerce.dto.request.user.UpdatePersonalInfoRequest;
import com.velsystems.ecommerce.dto.request.user.UpdateEmailRequest;
import com.velsystems.ecommerce.dto.request.user.UpdatePhoneRequest;
import com.velsystems.ecommerce.dto.request.user.VerifyUpdateOtpRequest;
import com.velsystems.ecommerce.dto.response.UserResponse;
import com.velsystems.ecommerce.dto.response.otp.OtpSendResponse;

public interface UserService {
    UserResponse updatePersonalInfo(UpdatePersonalInfoRequest request);

    OtpSendResponse requestEmailUpdateOtp(UpdateEmailRequest request);

    UserResponse verifyEmailUpdateOtp(VerifyUpdateOtpRequest request);

    OtpSendResponse requestPhoneUpdateOtp(UpdatePhoneRequest request);

    UserResponse verifyPhoneUpdateOtp(VerifyUpdateOtpRequest request);
}

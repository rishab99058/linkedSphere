package com.linksphere.auth_service.service;

import com.linksphere.auth_service.dto.request.ForgotPasswordRequest;
import com.linksphere.auth_service.dto.request.GoogleLoginRequest;
import com.linksphere.auth_service.dto.request.LoginRequest;
import com.linksphere.auth_service.dto.request.RefreshTokenRequest;
import com.linksphere.auth_service.dto.request.RegisterRequest;
import com.linksphere.auth_service.dto.request.ResetPasswordRequest;
import com.linksphere.auth_service.dto.response.ForgotPasswordRespose;
import com.linksphere.auth_service.dto.response.LoginResponse;
import com.linksphere.auth_service.dto.response.RefreshTokenResponse;
import com.linksphere.auth_service.dto.response.RegisterResponse;
import com.linksphere.auth_service.dto.response.ResetPasswordResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);

    void logout(String refreshToken);

    ForgotPasswordRespose forgotPassword(ForgotPasswordRequest request);

    ResetPasswordResponse resetPassword(ResetPasswordRequest request);

    LoginResponse googleLogin(GoogleLoginRequest request);

}

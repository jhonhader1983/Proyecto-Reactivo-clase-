package com.lamaison.auth.service;

import com.lamaison.auth.dto.request.LoginRequest;
import com.lamaison.auth.dto.request.PasswordResetRequest;
import com.lamaison.auth.dto.request.RegisterRequest;
import com.lamaison.auth.dto.response.AuthResponse;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<AuthResponse> register(RegisterRequest request);
    Mono<AuthResponse> login(LoginRequest request);
    Mono<Void> requestPasswordReset(PasswordResetRequest request);
    Mono<Void> resetPassword(String token, String newPassword);
}
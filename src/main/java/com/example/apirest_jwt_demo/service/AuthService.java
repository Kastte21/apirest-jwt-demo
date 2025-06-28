package com.example.apirest_jwt_demo.service;

import com.example.apirest_jwt_demo.dto.AuthResponse;
import com.example.apirest_jwt_demo.dto.LoginRequest;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(String refreshToken);
    void logout(String refreshToken);
}

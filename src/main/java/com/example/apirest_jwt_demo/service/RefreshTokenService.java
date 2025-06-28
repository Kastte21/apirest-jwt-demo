package com.example.apirest_jwt_demo.service;

import com.example.apirest_jwt_demo.model.RefreshToken;
import com.example.apirest_jwt_demo.model.User;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(User user);
    RefreshToken verifyToken(String token);
    void revokeToken(String token);
    void revokeAllUserTokens(User user);
}

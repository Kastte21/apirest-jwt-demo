package com.example.apirest_jwt_demo.service.impl;

import com.example.apirest_jwt_demo.dto.AuthResponse;
import com.example.apirest_jwt_demo.dto.LoginRequest;
import com.example.apirest_jwt_demo.model.RefreshToken;
import com.example.apirest_jwt_demo.model.User;
import com.example.apirest_jwt_demo.repository.UserRepository;
import com.example.apirest_jwt_demo.security.JwtUtil;
import com.example.apirest_jwt_demo.service.AuthService;
import com.example.apirest_jwt_demo.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(),
                        request.getPassword()
                )
        );

        String authenticatedUsername = authentication.getName();

        // Buscar el usuario correspondiente en la base de datos
        User user = userRepository.findByUsername(authenticatedUsername)
                .or(() -> userRepository.findByEmail(authenticatedUsername))
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado."));

        // Verifica si el usuario está habilitado
        if (!user.getEnable()) {
            throw new IllegalArgumentException("Usuario deshabilitado.");
        }

        // Genera tokens JWT
        String accessToken = jwtUtil.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .build();
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        RefreshToken storedToken = refreshTokenService.verifyToken(refreshToken);
        User user = storedToken.getUser();

        String newAccessToken = jwtUtil.generateToken(user);
        refreshTokenService.revokeToken(refreshToken);
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);
        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken.getToken())
                .tokenType("Bearer")
                .build();
    }

    @Override
    public  void logout(String refreshToken) {
        refreshTokenService.revokeToken(refreshToken);
    }
}
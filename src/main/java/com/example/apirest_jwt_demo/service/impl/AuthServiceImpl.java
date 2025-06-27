package com.example.apirest_jwt_demo.service.impl;

import com.example.apirest_jwt_demo.dto.AuthResponse;
import com.example.apirest_jwt_demo.dto.LoginRequest;
import com.example.apirest_jwt_demo.model.User;
import com.example.apirest_jwt_demo.repository.UserRepository;
import com.example.apirest_jwt_demo.security.JwtUtil;
import com.example.apirest_jwt_demo.service.AuthService;
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

    @Override
    public AuthResponse login(LoginRequest request) {
        // Intenta autenticar al usuario con Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(),
                        request.getPassword()
                )
        );

        // Obtener el nombre de usuario autenticado
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
        String refreshToken = jwtUtil.generateRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }
}
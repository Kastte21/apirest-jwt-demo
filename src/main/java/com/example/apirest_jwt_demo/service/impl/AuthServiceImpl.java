package com.example.apirest_jwt_demo.service.impl;

import com.example.apirest_jwt_demo.dto.AuthResponse;
import com.example.apirest_jwt_demo.dto.LoginRequest;
import com.example.apirest_jwt_demo.model.User;
import com.example.apirest_jwt_demo.repository.UserRepository;
import com.example.apirest_jwt_demo.security.JwtUtil;
import com.example.apirest_jwt_demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsernameOrEmail())
                .or(() -> userRepository.findByEmail(request.getUsernameOrEmail()))
                .orElseThrow(() -> new IllegalArgumentException("Usuario o contraseña inválidos."));

        if (!user.getEnable()) {
            throw new IllegalArgumentException("Usuario deshabilitado.");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw  new IllegalArgumentException("Usuario o contraseña inválidos.");
        }

        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }
}

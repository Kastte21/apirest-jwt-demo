package com.example.apirest_jwt_demo.service;

import com.example.apirest_jwt_demo.dto.UserRegisterRequest;
import com.example.apirest_jwt_demo.dto.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserResponse registerUser(UserRegisterRequest request);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
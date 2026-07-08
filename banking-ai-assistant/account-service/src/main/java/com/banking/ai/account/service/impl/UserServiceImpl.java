package com.banking.ai.account.service.impl;

import com.banking.ai.account.dto.LoginRequest;
import com.banking.ai.account.dto.LoginResponse;
import com.banking.ai.account.dto.RegisterRequest;
import com.banking.ai.account.dto.RegisterResponse;
import com.banking.ai.account.dto.UserResponse;
import com.banking.ai.account.entity.User;
import com.banking.ai.account.exception.EmailAlreadyExistsException;
import com.banking.ai.account.exception.InvalidCredentialsException;
import com.banking.ai.account.exception.UserNotFoundException;
import com.banking.ai.account.mapper.UserMapper;
import com.banking.ai.account.repository.UserRepository;
import com.banking.ai.account.service.AuditService;
import com.banking.ai.account.service.UserService;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuditService auditService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        String email = normalizeEmail(request.getEmail());
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Email already exists: " + email);
        }

        RegisterRequest normalizedRequest = new RegisterRequest();
        normalizedRequest.setFirstName(request.getFirstName());
        normalizedRequest.setLastName(request.getLastName());
        normalizedRequest.setEmail(email);
        normalizedRequest.setPassword(request.getPassword());
        normalizedRequest.setRole(request.getRole());

        User user = userMapper.toEntity(normalizedRequest);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);
        auditService.log("REGISTER", email, "User registered successfully");

        return new RegisterResponse("User registered successfully", userMapper.toResponse(savedUser));
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        String email = normalizeEmail(request.getEmail());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!user.isStatus() || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        auditService.log("LOGIN", email, "User logged in successfully");
        return new LoginResponse("Login successful", generateToken(user), userMapper.toResponse(user));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getProfile(String email) {
        String normalizedEmail = normalizeEmail(email);
        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + normalizedEmail));
        return userMapper.toResponse(user);
    }

    private String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }

    private String generateToken(User user) {
        String tokenPayload = user.getEmail() + ":" + UUID.randomUUID();
        return Base64.getUrlEncoder().withoutPadding()
                .encodeToString(tokenPayload.getBytes(StandardCharsets.UTF_8));
    }
}

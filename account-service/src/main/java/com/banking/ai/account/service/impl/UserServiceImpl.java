package com.banking.ai.account.service.impl;

import com.banking.ai.account.dto.RegisterRequest;
import com.banking.ai.account.dto.UserResponse;
import com.banking.ai.account.entity.User;
import com.banking.ai.account.exception.ResourceNotFoundException;
import com.banking.ai.account.exception.UserAlreadyExistsException;
import com.banking.ai.account.repository.UserRepository;
import com.banking.ai.account.service.AuditService;
import com.banking.ai.account.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuditService auditService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuditService auditService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.auditService = auditService;
    }

    @Override
    public UserResponse registerUser(RegisterRequest request) {
        String email = normalizeEmail(request.email());
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new UserAlreadyExistsException("A user with this email already exists");
        }

        User user = new User();
        applyRequest(user, request, email);
        user.setPassword(passwordEncoder.encode(request.password()));
        UserResponse response = toResponse(userRepository.save(user));
        auditService.logAction("USER_REGISTERED", email);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findUser(Long id) {
        UserResponse response = toResponse(findEntity(id));
        auditService.logAction("USER_PROFILE_VIEWED", response.email());
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findUserByEmail(String email) {
        UserResponse response = toResponse(userRepository.findByEmailIgnoreCase(normalizeEmail(email))
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));
        auditService.logAction("USER_PROFILE_VIEWED", response.email());
        return response;
    }

    @Override
    public UserResponse updateUser(Long id, RegisterRequest request) {
        User user = findEntity(id);
        String email = normalizeEmail(request.email());
        userRepository.findByEmailIgnoreCase(email)
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> { throw new UserAlreadyExistsException("A user with this email already exists"); });
        applyRequest(user, request, email);
        if (request.password() != null && !request.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }
        UserResponse response = toResponse(userRepository.save(user));
        auditService.logAction("USER_UPDATED", response.email());
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifyPassword(String email, String rawPassword) {
        boolean verified = userRepository.findByEmailIgnoreCase(normalizeEmail(email))
                .map(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
                .orElse(false);
        auditService.logAction(verified ? "USER_LOGIN_SUCCESS" : "USER_LOGIN_FAILED", normalizeEmail(email));
        return verified;
    }

    private User findEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    private void applyRequest(User user, RegisterRequest request, String email) {
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(email);
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getRole(), user.getCreatedDate(), user.getUpdatedDate(), user.getStatus());
    }
}

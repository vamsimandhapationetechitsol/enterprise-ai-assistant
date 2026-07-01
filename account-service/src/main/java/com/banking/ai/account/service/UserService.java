package com.banking.ai.account.service;

import com.banking.ai.account.dto.RegisterRequest;
import com.banking.ai.account.dto.UserResponse;

public interface UserService {
    UserResponse registerUser(RegisterRequest request);
    UserResponse findUser(Long id);
    UserResponse findUserByEmail(String email);
    UserResponse updateUser(Long id, RegisterRequest request);
    boolean verifyPassword(String email, String rawPassword);
}

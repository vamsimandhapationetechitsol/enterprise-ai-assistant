package com.banking.ai.account.service;

import com.banking.ai.account.dto.LoginRequest;
import com.banking.ai.account.dto.LoginResponse;
import com.banking.ai.account.dto.RegisterRequest;
import com.banking.ai.account.dto.RegisterResponse;
import com.banking.ai.account.dto.UserResponse;

public interface UserService {
    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    UserResponse getProfile(String email);
}

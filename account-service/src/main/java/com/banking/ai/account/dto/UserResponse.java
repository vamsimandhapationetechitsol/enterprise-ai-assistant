package com.banking.ai.account.dto;

import com.banking.ai.account.entity.User;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        User.Role role,
        LocalDateTime createdDate,
        LocalDateTime updatedDate,
        User.Status status
) {
}

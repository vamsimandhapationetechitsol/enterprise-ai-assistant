package com.banking.ai.account.dto;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {
}

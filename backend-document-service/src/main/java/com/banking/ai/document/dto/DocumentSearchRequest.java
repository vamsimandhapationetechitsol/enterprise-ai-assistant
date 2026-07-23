package com.banking.ai.document.dto;

import jakarta.validation.constraints.NotBlank;

public record DocumentSearchRequest(
        @NotBlank(message = "Search keyword is required")
        String keyword
) {
}

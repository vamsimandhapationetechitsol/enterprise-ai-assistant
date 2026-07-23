package com.banking.ai.document.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record DocumentRequest(
        @NotBlank(message = "Title is required")
        @Size(max = 150, message = "Title must be at most 150 characters")
        String title,

        @NotBlank(message = "Document type is required")
        String documentType,

        @NotBlank(message = "Category is required")
        String category,

        @NotBlank(message = "Owner email is required")
        @Email(message = "Owner email must be valid")
        String ownerEmail,

        @Size(max = 4000, message = "Summary must be at most 4000 characters")
        String summary,

        List<String> tags
) {
}

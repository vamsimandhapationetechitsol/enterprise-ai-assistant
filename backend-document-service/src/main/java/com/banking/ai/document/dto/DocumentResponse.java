package com.banking.ai.document.dto;

import com.banking.ai.document.entity.DocumentMetadata;

import java.time.LocalDateTime;
import java.util.List;

public record DocumentResponse(
        Long id,
        String title,
        String documentType,
        String category,
        String ownerEmail,
        String summary,
        List<String> tags,
        DocumentMetadata.Status status,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}

package com.banking.ai.document.dto;

import java.util.List;

public record DocumentPageResponse(
        List<DocumentResponse> documents,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}

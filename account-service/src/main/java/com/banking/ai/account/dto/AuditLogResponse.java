package com.banking.ai.account.dto;

import java.time.LocalDateTime;

public record AuditLogResponse(
        Long id,
        String action,
        String performedBy,
        LocalDateTime timestamp
) {
}

package com.banking.ai.account.mapper;

import com.banking.ai.account.dto.AuditLogResponse;
import com.banking.ai.account.entity.AuditLog;

public final class AuditMapper {

    private AuditMapper() {
    }

    public static AuditLogResponse toResponse(AuditLog auditLog) {
        return new AuditLogResponse(
                auditLog.getId(),
                auditLog.getAction(),
                auditLog.getPerformedBy(),
                auditLog.getTimestamp()
        );
    }
}

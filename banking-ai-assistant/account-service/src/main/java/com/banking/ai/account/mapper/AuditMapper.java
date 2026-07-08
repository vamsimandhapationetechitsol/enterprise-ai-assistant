package com.banking.ai.account.mapper;

import com.banking.ai.account.dto.AuditLogResponse;
import com.banking.ai.account.entity.AuditLog;
import org.springframework.stereotype.Component;

@Component
public class AuditMapper {

    public AuditLogResponse toResponse(AuditLog auditLog) {
        return new AuditLogResponse(
                auditLog.getId(),
                auditLog.getAction(),
                auditLog.getEmail(),
                auditLog.getDetails(),
                auditLog.getCreatedDate());
    }
}

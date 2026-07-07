package com.banking.ai.account.mapper;

import com.banking.ai.account.entity.AuditLog;
import com.banking.ai.account.dto.AuditLogResponse;

public class AuditMapper {

    public static AuditLogResponse toResponse(AuditLog auditLog) {

        AuditLogResponse response = new AuditLogResponse();

        response.setId(auditLog.getId());
        response.setAction(auditLog.getAction());
        response.setPerformedBy(auditLog.getPerformedBy());
        response.setTimestamp(auditLog.getTimestamp());

        return response;
    }
}
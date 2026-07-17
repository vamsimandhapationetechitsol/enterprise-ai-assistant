package com.banking.ai.account.service;

import com.banking.ai.account.dto.AuditLogResponse;

import java.util.List;

public interface AuditService {

    void logAction(String action, String performedBy);

    List<AuditLogResponse> getAuditLogs();
}

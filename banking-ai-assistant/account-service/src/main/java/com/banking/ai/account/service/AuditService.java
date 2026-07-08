package com.banking.ai.account.service;

import com.banking.ai.account.dto.AuditLogResponse;
import java.util.List;

public interface AuditService {
    void log(String action, String email, String details);

    List<AuditLogResponse> getLogsByEmail(String email);
}

package com.banking.ai.account.controller;

import com.banking.ai.account.dto.AuditLogResponse;
import com.banking.ai.account.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
@Tag(name = "Audit", description = "Database-backed audit trail for account-service activity")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    @Operation(summary = "Get latest audit log entries")
    public List<AuditLogResponse> getAuditLogs() {
        return auditService.getAuditLogs();
    }
}

package com.banking.ai.account.service.impl;

import com.banking.ai.account.dto.AuditLogResponse;
import com.banking.ai.account.entity.AuditLog;
import com.banking.ai.account.mapper.AuditMapper;
import com.banking.ai.account.repository.AuditLogRepository;
import com.banking.ai.account.service.AuditService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;
    private final AuditMapper auditMapper;

    @Override
    @Transactional
    public void log(String action, String email, String details) {
        AuditLog auditLog = new AuditLog();
        auditLog.setAction(action);
        auditLog.setEmail(email);
        auditLog.setDetails(details);
        auditLogRepository.save(auditLog);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditLogResponse> getLogsByEmail(String email) {
        return auditLogRepository.findByEmailOrderByCreatedDateDesc(email).stream()
                .map(auditMapper::toResponse)
                .toList();
    }
}

package com.banking.ai.account.service.impl;

import com.banking.ai.account.dto.AuditLogResponse;
import com.banking.ai.account.entity.AuditLog;
import com.banking.ai.account.mapper.AuditMapper;
import com.banking.ai.account.repository.AuditLogRepository;
import com.banking.ai.account.service.AuditService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logAction(String action, String performedBy) {
        auditLogRepository.save(new AuditLog(action, performedBy));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditLogResponse> getAuditLogs() {
        return auditLogRepository.findTop25ByOrderByTimestampDesc()
                .stream()
                .map(AuditMapper::toResponse)
                .collect(Collectors.toList());
    }
}

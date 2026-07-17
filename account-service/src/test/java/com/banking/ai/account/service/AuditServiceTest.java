package com.banking.ai.account.service;

import com.banking.ai.account.entity.AuditLog;
import com.banking.ai.account.repository.AuditLogRepository;
import com.banking.ai.account.service.impl.AuditServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditServiceTest {

    @Mock AuditLogRepository auditLogRepository;

    @Test
    void storesAuditActionForUserActivity() {
        AuditService auditService = new AuditServiceImpl(auditLogRepository);

        auditService.logAction("USER_LOGIN_SUCCESS", "vamsi@example.com");

        ArgumentCaptor<AuditLog> captor = ArgumentCaptor.forClass(AuditLog.class);
        verify(auditLogRepository).save(captor.capture());
        assertThat(captor.getValue().getAction()).isEqualTo("USER_LOGIN_SUCCESS");
        assertThat(captor.getValue().getPerformedBy()).isEqualTo("vamsi@example.com");
    }

    @Test
    void returnsLatestAuditLogs() {
        AuditLog auditLog = new AuditLog("USER_REGISTERED", "vamsi@example.com");
        when(auditLogRepository.findTop25ByOrderByTimestampDesc()).thenReturn(List.of(auditLog));

        AuditService auditService = new AuditServiceImpl(auditLogRepository);

        assertThat(auditService.getAuditLogs())
                .hasSize(1)
                .first()
                .extracting("action", "performedBy")
                .containsExactly("USER_REGISTERED", "vamsi@example.com");
    }
}

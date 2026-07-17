package com.banking.ai.account.repository;

import com.banking.ai.account.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findTop25ByOrderByTimestampDesc();
}

package com.banking.ai.account.audit.service;

public interface AuditService {

    void logAction(String action, String performedBy);

}
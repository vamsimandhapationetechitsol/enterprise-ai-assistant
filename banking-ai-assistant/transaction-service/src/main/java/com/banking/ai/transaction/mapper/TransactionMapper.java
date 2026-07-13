package com.banking.ai.transaction.mapper;

import com.banking.ai.transaction.dto.TransactionResponse;
import com.banking.ai.transaction.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getAccountId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getDescription(),
                transaction.getReferenceId(),
                transaction.getStatus(),
                transaction.getCreatedAt(),
                transaction.getProcessedAt());
    }
}

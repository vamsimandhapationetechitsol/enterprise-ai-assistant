package com.banking.ai.transaction.dto;

import com.banking.ai.transaction.entity.Transaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private String id;
    private String accountId;
    private String type;
    private BigDecimal amount;
    private String currency;
    private String description;
    private String referenceId;
    private Transaction.TransactionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
}

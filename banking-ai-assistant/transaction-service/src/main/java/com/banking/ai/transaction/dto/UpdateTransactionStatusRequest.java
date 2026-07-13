package com.banking.ai.transaction.dto;

import com.banking.ai.transaction.entity.Transaction;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTransactionStatusRequest {

    @NotNull(message = "Status is required")
    private Transaction.TransactionStatus status;
}

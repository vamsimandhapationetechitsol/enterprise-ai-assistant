package com.banking.ai.transaction.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateTransactionRequest {

    @NotBlank(message = "Account id is required")
    private String accountId;

    @NotBlank(message = "Transaction type is required")
    @Pattern(regexp = "DEBIT|CREDIT|TRANSFER", message = "Type must be DEBIT, CREDIT, or TRANSFER")
    private String type;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency must be a 3-letter code")
    private String currency = "USD";

    @Size(max = 255, message = "Description must be 255 characters or less")
    private String description;
}

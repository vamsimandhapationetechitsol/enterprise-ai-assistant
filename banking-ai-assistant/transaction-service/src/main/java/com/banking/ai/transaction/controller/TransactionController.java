package com.banking.ai.transaction.controller;

import com.banking.ai.transaction.dto.CreateTransactionRequest;
import com.banking.ai.transaction.dto.TransactionResponse;
import com.banking.ai.transaction.dto.UpdateTransactionStatusRequest;
import com.banking.ai.transaction.entity.Transaction;
import com.banking.ai.transaction.exception.TransactionNotFoundException;
import com.banking.ai.transaction.mapper.TransactionMapper;
import com.banking.ai.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByAccountId(
            @PathVariable String accountId) {
        return ResponseEntity.ok(transactionService.getTransactionsByAccountId(accountId).stream()
                .map(transactionMapper::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable String id) {
        Transaction transaction = transactionService.getTransactionById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found: " + id));
        return ResponseEntity.ok(transactionMapper.toResponse(transaction));
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody CreateTransactionRequest request) {
        Transaction transaction = transactionService.createTransaction(
                request.getAccountId(),
                request.getType(),
                request.getAmount(),
                request.getCurrency(),
                request.getDescription());
        return ResponseEntity.ok(transactionMapper.toResponse(transaction));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TransactionResponse> updateTransactionStatus(
            @PathVariable String id,
            @Valid @RequestBody UpdateTransactionStatusRequest request) {
        Transaction transaction = transactionService.updateTransactionStatus(id, request.getStatus());
        return ResponseEntity.ok(transactionMapper.toResponse(transaction));
    }
}

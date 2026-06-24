package com.banking.ai.transaction.controller;

import com.banking.ai.transaction.entity.Transaction;
import com.banking.ai.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(
            @PathVariable String accountId) {
        return ResponseEntity.ok(transactionService.getTransactionsByAccountId(accountId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable String id) {
        return transactionService.getTransactionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(
            @RequestBody Map<String, String> request) {
        String accountId = request.get("accountId");
        String type = request.get("type");
        BigDecimal amount = new BigDecimal(request.get("amount"));
        String currency = request.getOrDefault("currency", "USD");
        String description = request.get("description");
        Transaction transaction = transactionService.createTransaction(
                accountId, type, amount, currency, description);
        return ResponseEntity.ok(transaction);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Transaction> updateTransactionStatus(
            @PathVariable String id,
            @RequestBody Map<String, String> request) {
        Transaction.TransactionStatus status =
                Transaction.TransactionStatus.valueOf(request.get("status"));
        return transactionService.updateTransactionStatus(id, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

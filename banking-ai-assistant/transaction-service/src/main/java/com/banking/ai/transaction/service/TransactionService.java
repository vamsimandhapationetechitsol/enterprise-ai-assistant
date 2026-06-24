package com.banking.ai.transaction.service;

import com.banking.ai.transaction.entity.Transaction;
import com.banking.ai.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public List<Transaction> getTransactionsByAccountId(String accountId) {
        return transactionRepository.findByAccountIdOrderByCreatedAtDesc(accountId);
    }

    public Optional<Transaction> getTransactionById(String id) {
        return transactionRepository.findById(id);
    }

    @Transactional
    public Transaction createTransaction(String accountId, String type,
                                         BigDecimal amount, String currency,
                                         String description) {
        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId);
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setDescription(description);
        transaction.setStatus(Transaction.TransactionStatus.PENDING);
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Optional<Transaction> updateTransactionStatus(String id,
                                                          Transaction.TransactionStatus status) {
        return transactionRepository.findById(id).map(tx -> {
            tx.setStatus(status);
            if (status == Transaction.TransactionStatus.COMPLETED) {
                tx.setProcessedAt(LocalDateTime.now());
            }
            return transactionRepository.save(tx);
        });
    }
}

package com.banking.ai.transaction.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.banking.ai.transaction.entity.Transaction;
import com.banking.ai.transaction.exception.TransactionNotFoundException;
import com.banking.ai.transaction.repository.TransactionRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService(transactionRepository);
    }

    @Test
    void createTransactionStoresPendingTransactionWithReferenceId() {
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> {
            Transaction transaction = invocation.getArgument(0);
            transaction.setId("txn-1");
            transaction.setCreatedAt(LocalDateTime.now());
            return transaction;
        });

        Transaction transaction = transactionService.createTransaction(
                "acc-1", "DEBIT", new BigDecimal("25.00"), "usd", "ATM withdrawal");

        assertThat(transaction.getStatus()).isEqualTo(Transaction.TransactionStatus.PENDING);
        assertThat(transaction.getCurrency()).isEqualTo("USD");
        assertThat(transaction.getReferenceId()).startsWith("TXN-");

        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository).save(captor.capture());
        assertThat(captor.getValue().getAmount()).isEqualByComparingTo("25.00");
    }

    @Test
    void updateTransactionStatusSetsProcessedAtWhenCompleted() {
        Transaction existing = new Transaction();
        existing.setId("txn-1");
        existing.setStatus(Transaction.TransactionStatus.PENDING);
        when(transactionRepository.findById("txn-1")).thenReturn(Optional.of(existing));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transaction updated = transactionService.updateTransactionStatus(
                "txn-1", Transaction.TransactionStatus.COMPLETED);

        assertThat(updated.getStatus()).isEqualTo(Transaction.TransactionStatus.COMPLETED);
        assertThat(updated.getProcessedAt()).isNotNull();
    }

    @Test
    void updateTransactionStatusThrowsWhenMissing() {
        when(transactionRepository.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transactionService.updateTransactionStatus(
                "missing", Transaction.TransactionStatus.FAILED))
                .isInstanceOf(TransactionNotFoundException.class);
    }
}

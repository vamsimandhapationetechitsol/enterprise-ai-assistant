package com.banking.ai.transaction.repository;

import com.banking.ai.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findByAccountIdOrderByCreatedAtDesc(String accountId);

    List<Transaction> findByAccountIdAndTypeOrderByCreatedAtDesc(String accountId, String type);

    List<Transaction> findByStatusOrderByCreatedAtDesc(Transaction.TransactionStatus status);
}

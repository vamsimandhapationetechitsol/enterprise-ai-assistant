package com.banking.ai.account.repository;

import com.banking.ai.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    List<Account> findByUserId(String userId);

    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByUserIdAndAccountType(String userId, Account.AccountType accountType);

    boolean existsByAccountNumber(String accountNumber);
}

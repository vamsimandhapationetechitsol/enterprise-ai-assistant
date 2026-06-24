package com.banking.ai.account.service;

import com.banking.ai.account.entity.Account;
import com.banking.ai.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;

    public List<Account> getAccountsByUserId(String userId) {
        log.info("Fetching accounts for user: {}", userId);
        return accountRepository.findByUserId(userId);
    }

    public Optional<Account> getAccountById(String accountId) {
        return accountRepository.findById(accountId);
    }

    public Optional<Account> getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Transactional
    public Account createAccount(String userId, Account.AccountType accountType, String currency) {
        Account account = new Account();
        account.setUserId(userId);
        account.setAccountType(accountType);
        account.setCurrency(currency != null ? currency : "USD");
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(BigDecimal.ZERO);
        account.setStatus(Account.AccountStatus.ACTIVE);
        log.info("Creating account for user: {}, type: {}", userId, accountType);
        return accountRepository.save(account);
    }

    @Transactional
    public Account updateBalance(String accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountId));
        account.setBalance(account.getBalance().add(amount));
        return accountRepository.save(account);
    }

    @Transactional
    public Account updateAccountStatus(String accountId, Account.AccountStatus status) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountId));
        account.setStatus(status);
        return accountRepository.save(account);
    }

    private String generateAccountNumber() {
        String number;
        do {
            number = "ACC" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
        } while (accountRepository.existsByAccountNumber(number));
        return number;
    }
}

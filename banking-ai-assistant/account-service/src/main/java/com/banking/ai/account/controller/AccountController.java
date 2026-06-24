package com.banking.ai.account.controller;

import com.banking.ai.account.entity.Account;
import com.banking.ai.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Account>> getAccountsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(accountService.getAccountsByUserId(userId));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable String accountId) {
        return accountService.getAccountById(accountId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/number/{accountNumber}")
    public ResponseEntity<Account> getAccountByNumber(@PathVariable String accountNumber) {
        return accountService.getAccountByNumber(accountNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        Account.AccountType accountType = Account.AccountType.valueOf(request.getOrDefault("accountType", "CHECKING"));
        String currency = request.get("currency");
        Account account = accountService.createAccount(userId, accountType, currency);
        return ResponseEntity.ok(account);
    }

    @PatchMapping("/{accountId}/status")
    public ResponseEntity<Account> updateAccountStatus(
            @PathVariable String accountId,
            @RequestBody Map<String, String> request) {
        Account.AccountStatus status = Account.AccountStatus.valueOf(request.get("status"));
        return ResponseEntity.ok(accountService.updateAccountStatus(accountId, status));
    }
}

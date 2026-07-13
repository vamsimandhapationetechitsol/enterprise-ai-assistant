package com.banking.ai.transaction.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.banking.ai.transaction.entity.Transaction;
import com.banking.ai.transaction.exception.GlobalExceptionHandler;
import com.banking.ai.transaction.mapper.TransactionMapper;
import com.banking.ai.transaction.service.TransactionService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TransactionController.class)
@Import({TransactionMapper.class, GlobalExceptionHandler.class})
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    void createTransactionReturnsOk() throws Exception {
        when(transactionService.createTransaction(eq("acc-1"), eq("DEBIT"), any(), eq("USD"), eq("ATM withdrawal")))
                .thenReturn(transaction());

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "accountId": "acc-1",
                                  "type": "DEBIT",
                                  "amount": 25.00,
                                  "currency": "USD",
                                  "description": "ATM withdrawal"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("txn-1"))
                .andExpect(jsonPath("$.referenceId").value("TXN-123456789ABC"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void createTransactionValidatesRequest() throws Exception {
        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "accountId": "",
                                  "type": "INVALID",
                                  "amount": 0,
                                  "currency": "US"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTransactionsByAccountIdReturnsList() throws Exception {
        when(transactionService.getTransactionsByAccountId("acc-1")).thenReturn(List.of(transaction()));

        mockMvc.perform(get("/api/transactions/account/acc-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("txn-1"))
                .andExpect(jsonPath("$[0].accountId").value("acc-1"));
    }

    @Test
    void updateStatusReturnsOk() throws Exception {
        Transaction transaction = transaction();
        transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
        transaction.setProcessedAt(LocalDateTime.now());
        when(transactionService.updateTransactionStatus("txn-1", Transaction.TransactionStatus.COMPLETED))
                .thenReturn(transaction);

        mockMvc.perform(patch("/api/transactions/txn-1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"COMPLETED\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"))
                .andExpect(jsonPath("$.processedAt").exists());
    }

    @Test
    void getTransactionByIdReturnsNotFound() throws Exception {
        when(transactionService.getTransactionById("missing")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/transactions/missing"))
                .andExpect(status().isNotFound());
    }

    private Transaction transaction() {
        Transaction transaction = new Transaction();
        transaction.setId("txn-1");
        transaction.setAccountId("acc-1");
        transaction.setType("DEBIT");
        transaction.setAmount(new BigDecimal("25.00"));
        transaction.setCurrency("USD");
        transaction.setDescription("ATM withdrawal");
        transaction.setReferenceId("TXN-123456789ABC");
        transaction.setStatus(Transaction.TransactionStatus.PENDING);
        transaction.setCreatedAt(LocalDateTime.now());
        return transaction;
    }
}

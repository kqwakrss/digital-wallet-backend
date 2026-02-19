package com.example.wallet.wallet_backend.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wallet.wallet_backend.dto.DepositRequest;
import com.example.wallet.wallet_backend.dto.TransferRequest;
import com.example.wallet.wallet_backend.service.WalletService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.wallet.wallet_backend.domain.transaction.Transaction;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;
    @PostMapping("/{userId}/deposit")
    public ResponseEntity<BigDecimal> deposit(@PathVariable Long userId, @Valid @RequestBody DepositRequest request) {
        BigDecimal newBalance = walletService.deposit(userId, request.getAmount());
        return ResponseEntity.ok(newBalance);
    }
    @PostMapping("/{userId}/withdraw")
    public ResponseEntity<BigDecimal> withdraw(@PathVariable Long userId, @Valid @RequestBody DepositRequest request) {
        BigDecimal newBalance = walletService.withdraw(userId, request.getAmount());
        return ResponseEntity.ok(newBalance);
    }
    @GetMapping("/{userId}/balance")
    public ResponseEntity <BigDecimal> getbalance(@PathVariable Long userId) {
        BigDecimal balance = walletService.getBalance(userId);
        return ResponseEntity.ok(balance);
    }
    @PostMapping("/transfer")
    public ResponseEntity <BigDecimal> transfer(@Valid @RequestBody TransferRequest request) {
        BigDecimal newBalance = walletService.transfer(request);
        return ResponseEntity.ok(newBalance);
    }
    @GetMapping("/{id}/transactions")
    public ResponseEntity<Page<Transaction>> getTransactions(@PathVariable Long id, Pageable pageable) {
       Page<Transaction> transactions = walletService.getWalletTransactions(id, pageable);

       return ResponseEntity.ok(transactions);
    }
    
    
    
}

package com.example.wallet.wallet_backend.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.example.wallet.wallet_backend.repository.WalletRepository;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository){
        this.walletRepository = walletRepository;
    }
    public BigDecimal deposit(Long userId, BigDecimal amount) {
        var walletOptional = walletRepository.findByUserId(userId);
        if (walletOptional.isEmpty()){
            throw new RuntimeException("Wallet not found");
        } 
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("invalid amount");
        }
        var wallet = walletOptional.get();
        wallet.setBalance(wallet.getBalance().add(amount));

        return wallet.getBalance();
    }
    public BigDecimal withdraw(Long userId, BigDecimal amount) {
        var walletOptional = walletRepository.findByUserId(userId);
        if (walletOptional.isEmpty()){
            throw new RuntimeException("Wallet not found");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new RuntimeException("invalid amount");
        }
        var wallet = walletOptional.get();

        if (wallet.getBalance().compareTo(amount) < 0){
            throw new RuntimeException("Not enough balance");
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));

        return wallet.getBalance();
    }
    public BigDecimal getBalance(Long userId){
        var wallet = walletRepository.findByUserId(userId)
            .orElseThrow(()-> new RuntimeException("wallet not found"));
        return wallet.getBalance();
    }
}
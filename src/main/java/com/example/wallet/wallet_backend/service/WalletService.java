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
    public void deposit(BigDecimal amount, Long userId) {
        var walletOptional = walletRepository.findByUserId(userId);
        if (walletOptional.isEmpty()){
            throw new RuntimeException("Wallet not found");
        } 
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("invalid amount");
        }
        var wallet = walletOptional.get();
        wallet.setBalance(wallet.getBalance().add(amount));
    }
    public void withdraw(BigDecimal amount, Long userId, BigDecimal balance) {
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
    }
}
// 1. знайти wallet по userId.
// 2. якщо нема → помилка.
// 3. перевірити amount > 0.
// 4. змінити баланс
// 5. створити transaction
// 6. зберегти wallet і transaction

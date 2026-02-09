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
    }
    public void withdraw(BigDecimal amount, Long userId) {
        
    }
}
// 1. знайти wallet по userId
// 2. якщо нема → помилка
// 3. перевірити amount > 0
// 4. змінити баланс
// 5. створити transaction
// 6. зберегти wallet і transaction

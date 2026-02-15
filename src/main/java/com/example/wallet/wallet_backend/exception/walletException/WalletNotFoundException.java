package com.example.wallet.wallet_backend.exception.walletException;

public class WalletNotFoundException extends RuntimeException{

    private final Long userId;

    public WalletNotFoundException(Long userId){
        super("Wallet not found userId: " + userId);
        this.userId = userId;
    }
    public Long getUserId(){
        return userId;
    }
}
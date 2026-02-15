package com.example.wallet.wallet_backend.exception.transactionException;

import java.math.BigDecimal;

public class NotEnoughBalanceException extends RuntimeException{
    private final BigDecimal currentBalance;
    private final BigDecimal requestedAmount;

    public NotEnoughBalanceException (BigDecimal currentBalance, BigDecimal requestedAmount){
        super("Not enough balance. Current: " + currentBalance + ", requested: " + requestedAmount);
        this.currentBalance = currentBalance;
        this.requestedAmount = requestedAmount;
    }
    public BigDecimal getRequestedAmount(){
        return requestedAmount;
    }
    public BigDecimal getCurrentBalance(){
        return currentBalance;
    }
}

package com.example.wallet.wallet_backend.exception.common;

import java.math.BigDecimal;

public class InvalidAmountException extends RuntimeException {
    private final BigDecimal requestedAmount;

    public InvalidAmountException(BigDecimal requestedAmount){
        super("" + requestedAmount);
        this.requestedAmount = requestedAmount;
    }
    public BigDecimal getRequestedAmount(){
        return requestedAmount;
    }
}

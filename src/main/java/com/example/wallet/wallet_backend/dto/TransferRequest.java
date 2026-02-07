package com.example.wallet.wallet_backend.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TransferRequest {
    
    @NotNull
    private final Long fromUserId;
    @NotNull
    private final Long toUserId;

    @NotNull
    @Positive
    @DecimalMin(value = "0.01")
    private final BigDecimal amount;

    public Long GetFromUserId(){
        return fromUserId;
    }
    public Long GetToUserId(){
        return toUserId;
    }
    public BigDecimal GetAmount(){
        return amount;
    }
    public TransferRequest(BigDecimal amount, Long toUserId, Long fromUserId){
        this.amount = amount;
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
    }
}
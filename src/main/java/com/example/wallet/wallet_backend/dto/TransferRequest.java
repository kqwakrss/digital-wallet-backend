package com.example.wallet.wallet_backend.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class TransferRequest {
    
    @NotNull
    private final Long fromUserId;
    @NotNull
    private final Long toUserId;

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true)
    private final BigDecimal amount;

    public Long getFromUserId(){
        return fromUserId;
    }
    public Long getToUserId(){
        return toUserId;
    }
    public BigDecimal getAmount(){
        return amount;
    }
    public TransferRequest(Long fromUserId, Long toUserId, BigDecimal amount){
        this.amount = amount;
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
    }
}
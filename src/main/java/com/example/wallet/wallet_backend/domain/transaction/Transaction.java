package com.example.wallet.wallet_backend.domain.transaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.wallet.wallet_backend.domain.wallet.Wallet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table (name = "transactions")

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected Transaction(){
        
    }

    public Transaction(Wallet wallet, 
        BigDecimal amount,
        TransactionType type,
        TransactionStatus status
    ){
        this.wallet = wallet;
        this.amount = amount;
        this.type = type;
        this.status = status;
    }

    public BigDecimal getAmount(){
        return amount;
    }
    public Long getTransactionId(){
        return transactionId;
    }
    public TransactionType getType(){
        return type;
    }
    public TransactionStatus getStatus(){
        return status;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    public Wallet getWallet() {
        return wallet;
    }
}
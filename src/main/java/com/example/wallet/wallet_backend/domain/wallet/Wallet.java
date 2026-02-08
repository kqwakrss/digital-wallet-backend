package com.example.wallet.wallet_backend.domain.wallet;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.example.wallet.wallet_backend.domain.transaction.Transaction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id" ,nullable = false)
    private Long userId;
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;
    @Column(name = "created_at" ,nullable = false)
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "wallet", fetch = FetchType.LAZY)

    private List<Transaction> transactions;

    protected Wallet(){

    }
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<com.example.wallet.wallet_backend.domain.transaction.Transaction> getTransactions() {
        return transactions;
    }
}


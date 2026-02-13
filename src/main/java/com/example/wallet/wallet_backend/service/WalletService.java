    package com.example.wallet.wallet_backend.service;

    import java.math.BigDecimal;

    import org.springframework.stereotype.Service;

import com.example.wallet.wallet_backend.domain.transaction.Transaction;
import com.example.wallet.wallet_backend.domain.transaction.TransactionStatus;
import com.example.wallet.wallet_backend.domain.transaction.TransactionType;
    import com.example.wallet.wallet_backend.domain.wallet.Wallet;
    import com.example.wallet.wallet_backend.dto.TransferRequest;
    import com.example.wallet.wallet_backend.repository.TransactionRepository;
    import com.example.wallet.wallet_backend.repository.WalletRepository;

    import jakarta.transaction.Transactional;

    @Service
    public class WalletService {
        private final WalletRepository walletRepository;
        private final TransactionRepository transactionRepository;    

        public WalletService(WalletRepository walletRepository, TransactionRepository transactionRepository){
            this.walletRepository = walletRepository;
            this.transactionRepository = transactionRepository;
        }
        @Transactional
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
            
            Transaction transaction = new Transaction(
                wallet,
                amount,
                TransactionType.DEPOSIT,
                TransactionStatus.SUCCESS
            );
            
            transactionRepository.save(transaction);

            return wallet.getBalance();
        }
        @Transactional
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

            Transaction transaction = new Transaction(
                wallet,
                amount,
                TransactionType.WITHDRAW,
                TransactionStatus.SUCCESS
            );

            transactionRepository.save(transaction);

            return wallet.getBalance();
        }
        public BigDecimal getBalance(Long userId){
            var wallet = walletRepository.findByUserId(userId)
                .orElseThrow(()-> new RuntimeException("wallet not found"));
            return wallet.getBalance();
        }
        @Transactional
        public BigDecimal transfer(TransferRequest request){
            Wallet fromWallet = walletRepository
                .findByUserId(request.getFromUserId())
                .orElseThrow(()-> new RuntimeException("Sender not found"));
            if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0){
                throw new RuntimeException("amount must be bigger then 0");
            }

            if (request.getFromUserId().equals(request.getToUserId())){
                throw new IllegalArgumentException("can't transfer to the same user");}
            Wallet toWallet = walletRepository
                .findByUserId(request.getToUserId())
                .orElseThrow(()-> new RuntimeException("Receiver not found"));
            if (fromWallet.getBalance().compareTo(request.getAmount()) < 0){
                throw new RuntimeException("insufficient balance");
            }
            fromWallet.setBalance(fromWallet.getBalance().subtract(request.getAmount()));
            toWallet.setBalance(toWallet.getBalance().add(request.getAmount()));

            Transaction outgoing = new Transaction(
                fromWallet,
                request.getAmount(),
                TransactionType.TRANSFER,
                TransactionStatus.SUCCESS
            );
            Transaction incoming = new Transaction(
                toWallet,
                request.getAmount(),
                TransactionType.TRANSFER,
                TransactionStatus.SUCCESS
            );

            transactionRepository.save(outgoing);
            transactionRepository.save(incoming);

            return fromWallet.getBalance();
        }
    }
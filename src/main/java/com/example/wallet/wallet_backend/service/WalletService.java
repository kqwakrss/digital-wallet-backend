    package com.example.wallet.wallet_backend.service;

    import java.math.BigDecimal;
    import java.util.Objects;


    import org.springframework.stereotype.Service;

    import com.example.wallet.wallet_backend.domain.transaction.Transaction;
    import com.example.wallet.wallet_backend.domain.transaction.TransactionStatus;
    import com.example.wallet.wallet_backend.domain.transaction.TransactionType;
    import com.example.wallet.wallet_backend.domain.wallet.Wallet;
    import com.example.wallet.wallet_backend.dto.TransferRequest;
    import com.example.wallet.wallet_backend.repository.TransactionRepository;
    import com.example.wallet.wallet_backend.repository.WalletRepository;
    import com.example.wallet.wallet_backend.exception.walletException.WalletNotFoundException;
    import com.example.wallet.wallet_backend.exception.transactionException.NotEnoughBalanceException;
import com.example.wallet.wallet_backend.exception.transactionException.TransferToSameUserException;
import com.example.wallet.wallet_backend.exception.common.InvalidAmountException;
    import org.springframework.transaction.annotation.Transactional;


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

            validateAmount(amount);

            Wallet wallet = getWalletOrThrow(userId);

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

            validateAmount(amount);

            Wallet wallet = getWalletOrThrow(userId);

            if (wallet.getBalance().compareTo(amount) < 0){
                throw new NotEnoughBalanceException(wallet.getBalance(), amount);
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

        @Transactional(readOnly = true)
        public BigDecimal getBalance(Long userId){
            Wallet wallet = getWalletOrThrow(userId);
            return wallet.getBalance();
        }

        @Transactional
        public BigDecimal transfer(TransferRequest request){

            Long fromUserId = request.getFromUserId();
            Long toUserId = request.getToUserId();

            validateDifferentUsers(fromUserId, toUserId);

            BigDecimal amount = request.getAmount();

            validateAmount(amount);

            Wallet fromWallet = getWalletOrThrow(fromUserId);
            Wallet toWallet = getWalletOrThrow(toUserId);

            validateSufficientBalance(fromWallet, amount);

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


        private void validateAmount(BigDecimal amount){
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
                throw new InvalidAmountException(amount);
            }
        }
        private Wallet getWalletOrThrow(Long userId) {
            return walletRepository.findByUserId(userId)
                    .orElseThrow(() -> new WalletNotFoundException(userId));

        }
        private void validateDifferentUsers(Long fromUserId, Long toUserId){
            if (Objects.equals(fromUserId, toUserId)){
                throw new TransferToSameUserException(fromUserId);
            }
        }
        private void validateSufficientBalance(Wallet wallet, BigDecimal amount){
            if (wallet.getBalance().compareTo(amount) < 0){
                throw new NotEnoughBalanceException(wallet.getBalance(),amount);
            }
        }
    }
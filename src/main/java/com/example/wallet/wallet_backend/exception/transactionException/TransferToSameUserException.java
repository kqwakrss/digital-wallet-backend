package com.example.wallet.wallet_backend.exception.transactionException;

public class TransferToSameUserException extends RuntimeException{
    private final Long fromUserId;

    public TransferToSameUserException (Long fromUserId){
        super("Cannot transfer to the same user. userId:" + fromUserId);
        this.fromUserId = fromUserId;
    }
    public Long getFromUserId(){
        return fromUserId;
    }
}

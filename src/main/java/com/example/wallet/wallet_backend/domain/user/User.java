package com.example.wallet.wallet_backend.domain.user;

public class User {
    private String login;
    private String password;
    private String email;
    private UserStatus status;
    public User(String login){
        this.login = login;
        this.status = UserStatus.INACTIVE;
    }
    public String getLogin(){
        return login;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public UserStatus getStatus(){
        return status;
    }
}

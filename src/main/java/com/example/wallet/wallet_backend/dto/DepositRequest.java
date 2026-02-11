package com.example.wallet.wallet_backend.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DepositRequest {
    @NotNull
    private Long userId;

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true)
    private BigDecimal amount;

    @NotBlank
    @Size(min = 7, max = 20)
    private String password;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 3, max = 30)
    private String login;
    
    public String getLogin(){
        return login;      
    }

    public void setLogin(String login){
        this.login = login;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setUserId(Long userId){
        this.userId = userId;
    }
    public Long getUserId(){
        return userId;
    }
    public BigDecimal getAmount(){
        return amount;
    }
    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }
}

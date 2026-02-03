package com.example.wallet.wallet_backend.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true, length = 50,updatable = false)
    @NotBlank
    @Size(min = 3, max = 50)
    private String login;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;
    protected User(){

    }
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
    public long getId(){
        return id;
    }
}

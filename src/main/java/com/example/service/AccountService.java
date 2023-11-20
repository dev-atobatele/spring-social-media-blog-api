package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    public Object registerAccount() {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    public Object login() {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}

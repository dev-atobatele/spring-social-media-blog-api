package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
    public Object registerAccount(Account account) {
        if(account.getUsername().isBlank()
        || account.getPassword().length()<4){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            
        }
        for (Account account2 : accountRepository.findAll()) {
            if(account.getUsername().equals(account2.getUsername())){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        accountRepository.save(account);
        return accountRepository.getById(account.getAccount_id());
    }
    public Object login(Account account){
        for (Account account2 : accountRepository.findAll()) {
            if(account.getUsername().equals(account2.getUsername())
            && account.getPassword().equals(account2.getPassword())){
                return account2;
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}

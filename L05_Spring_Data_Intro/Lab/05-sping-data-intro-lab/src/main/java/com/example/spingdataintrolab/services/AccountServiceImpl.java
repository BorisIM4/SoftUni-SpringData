package com.example.spingdataintrolab.services;

import com.example.spingdataintrolab.repositories.AccountRepository;
import com.example.spingdataintrolab.repositories.UserRepository;

import java.math.BigDecimal;

public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal money, Long id) {

    }

    @Override
    public void transferMoney(BigDecimal money, Long id) {

    }
}

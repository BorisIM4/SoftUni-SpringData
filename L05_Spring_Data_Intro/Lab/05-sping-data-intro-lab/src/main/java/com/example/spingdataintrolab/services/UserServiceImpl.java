package com.example.spingdataintrolab.services;

import com.example.spingdataintrolab.model.Account;
import com.example.spingdataintrolab.model.User;
import com.example.spingdataintrolab.repositories.AccountRepository;
import com.example.spingdataintrolab.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private AccountRepository accountRepository;

    public UserServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public void registerUser(String username, int age, BigDecimal initialAmount) {
        //Create User
        var user = new User();
        user.setUsername(username);
        user.setAge(age);

        this.userRepository.save(user);

        //Create Account
        var firstAccount = new Account();
        firstAccount.setUser(user);
        firstAccount.setAmount(initialAmount);

        this.accountRepository.save(firstAccount);
    }
}

package com.example.spingdataintrolab.services;

import com.example.spingdataintrolab.model.Account;
import com.example.spingdataintrolab.model.User;
import com.example.spingdataintrolab.repositories.AccountRepository;
import com.example.spingdataintrolab.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private AccountRepository accountRepository;

    public UserServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void registerUser(User user, Account account) {

        this.userRepository.save(user);

        this.accountRepository.save(account);
    }
}

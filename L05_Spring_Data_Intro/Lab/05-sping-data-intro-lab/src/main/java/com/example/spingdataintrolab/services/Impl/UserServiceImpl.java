package com.example.spingdataintrolab.services.Impl;

import com.example.spingdataintrolab.exception.UserNameAlreadyExistException;
import com.example.spingdataintrolab.exception.UserNotFoundException;
import com.example.spingdataintrolab.model.Account;
import com.example.spingdataintrolab.model.User;
import com.example.spingdataintrolab.repositories.AccountRepository;
import com.example.spingdataintrolab.repositories.UserRepository;
import com.example.spingdataintrolab.services.Interfaces.UserService;
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
    public void registerUser(String username, int age, BigDecimal amount) throws UserNameAlreadyExistException {
        if (this.userRepository.existsByUsername(username)) {
            throw new UserNameAlreadyExistException();
        }

        //Create User
        var user = new User();
        user.setUsername(username);
        user.setAge(age);

        this.userRepository.save(user);

        //Create Account
        this.saveAccount(amount, user);
    }

    @Override
    public void addAccount(BigDecimal amount, long id) throws UserNotFoundException {
        User user = this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        this.saveAccount(amount, user);
    }

    private void saveAccount(BigDecimal amount, User user) {
        var account = new Account();
        account.setUser(user);
        account.setAmount(amount);

        this.accountRepository.save(account);
    }
}

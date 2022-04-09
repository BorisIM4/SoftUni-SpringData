package com.example.spingdataintrolab.services.Interfaces;

import com.example.spingdataintrolab.exception.UserNameAlreadyExistException;
import com.example.spingdataintrolab.exception.UserNotFoundException;
import com.example.spingdataintrolab.model.Account;
import com.example.spingdataintrolab.model.User;

import java.math.BigDecimal;

public interface UserService {

    void registerUser(
            String username,
            int age,
            BigDecimal initialAmount
    ) throws UserNameAlreadyExistException;

    void addAccount(BigDecimal amount, long id) throws UserNotFoundException;
}

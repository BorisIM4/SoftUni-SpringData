package com.example.spingdataintrolab.services;

import com.example.spingdataintrolab.model.Account;
import com.example.spingdataintrolab.model.User;

import java.math.BigDecimal;

public interface UserService {

    void registerUser(String username,  int age, BigDecimal initialAmount);
}

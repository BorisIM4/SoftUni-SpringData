package com.example.spingdataintrolab.services.Interfaces;

import com.example.spingdataintrolab.exception.InsufficientFundsException;

import java.math.BigDecimal;

public interface AccountService {

    void transferBetweenAccounts(Long from, Long to, BigDecimal amount) throws InsufficientFundsException;

    void withdrawMoney(BigDecimal money, Long id) throws InsufficientFundsException;

    void transferMoney(BigDecimal money, Long id);
}

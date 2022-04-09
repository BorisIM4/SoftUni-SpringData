package com.example.spingdataintrolab.services.Impl;

import com.example.spingdataintrolab.exception.InsufficientFundsException;
import com.example.spingdataintrolab.model.Account;
import com.example.spingdataintrolab.repositories.AccountRepository;
import com.example.spingdataintrolab.repositories.UserRepository;
import com.example.spingdataintrolab.services.Interfaces.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void transferBetweenAccounts(Long fromId, Long toId, BigDecimal amount) throws InsufficientFundsException {
        this.withdrawMoney(
                amount,
                fromId
        );

        this.transferMoney(
                amount,
                toId
        );
    }

    @Override
    public void withdrawMoney(BigDecimal money, Long id) throws InsufficientFundsException {
        Account account = this.getAccount(id);
        throwIfInsufficientFunds(money, account);

        account.setAmount(account.getAmount().subtract(money));
        this.accountRepository.save(account);
    }



    @Override
    public void transferMoney(BigDecimal money, Long id) {
        Account account = this.getAccount(id);

        account.setAmount(account.getAmount().add(money));
        this.accountRepository.save(account);
    }

    private void throwIfInsufficientFunds(BigDecimal money, Account account) throws InsufficientFundsException {
        if (account.getAmount().compareTo(money) < 0) {
            throw new InsufficientFundsException();
        }
    }

    private Account getAccount(Long id) {
        return this.accountRepository.findById(id)
                .orElseThrow();
    }
}

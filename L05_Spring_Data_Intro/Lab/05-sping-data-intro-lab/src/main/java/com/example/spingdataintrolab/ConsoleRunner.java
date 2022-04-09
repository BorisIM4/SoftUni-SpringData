package com.example.spingdataintrolab;

import com.example.spingdataintrolab.exception.InsufficientFundsException;
import com.example.spingdataintrolab.exception.UserNameAlreadyExistException;
import com.example.spingdataintrolab.services.Interfaces.AccountService;
import com.example.spingdataintrolab.services.Interfaces.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ConsoleRunner implements CommandLineRunner {

    private final UserService userService;
    private final AccountService accountService;

    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {

        try {
            this.accountService.transferBetweenAccounts(
                    4L,
                    1L,
                    BigDecimal.valueOf(10000)
            );
        } catch (InsufficientFundsException e) {
            e.printStackTrace();
        }

        //Register new user
//        try {
//            this.userService.registerUser(
//                    "Boris",
//                    32,
//                    new BigDecimal(11000)
//            );
//        } catch (UserNameAlreadyExistException e) {
//            System.out.println(e.getClass().getSimpleName());
//        }

        //Add account to existing User
//        this.userService.addAccount(
//                BigDecimal.valueOf(5500),
//                1
//        );

        //Withdraw money
//        try {
//            this.accountService.withdrawMoney(new BigDecimal(550), 1L);
//        } catch (InsufficientFundsException e) {
//            System.out.println(e.getClass().getSimpleName());
//        }
//
//        this.accountService.transferMoney(new BigDecimal(3333), 4L);

    }
}

package com.example.spingdataintrolab;

import com.example.spingdataintrolab.model.Account;
import com.example.spingdataintrolab.model.User;
import com.example.spingdataintrolab.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ConsoleRunner implements CommandLineRunner {

    private final UserService userService;

    public ConsoleRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {

        this.userService.registerUser(
                "Anni",
                6,
                new BigDecimal(100)
        );

    }
}

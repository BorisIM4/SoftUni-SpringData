package com.example.jsonexer.service;

import com.example.jsonexer.model.dto.UserSoldDto;
import com.example.jsonexer.model.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void seedUsers() throws IOException;

    User findRandomUsr();

    List<UserSoldDto> findAllUsersWithMoreThanOneSoldProducts();
}

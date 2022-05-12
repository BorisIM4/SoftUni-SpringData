package com.example.automappingdtoexer.service;

import com.example.automappingdtoexer.model.dto.UserLoginDto;
import com.example.automappingdtoexer.model.dto.UserRegisterDto;

public interface UserService {
    void registerUser(UserRegisterDto userRegisterDto);

    void loginUser(UserLoginDto userLoginDto);

    void logout();
}

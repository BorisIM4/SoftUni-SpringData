package com.example.automappingdtoexer.service;

import com.example.automappingdtoexer.model.dto.GameAddDto;

import java.math.BigDecimal;

public interface GameService {
    void addGame(GameAddDto gameAddDto);

    void editGame(Long gameId, BigDecimal price, Double size);
}

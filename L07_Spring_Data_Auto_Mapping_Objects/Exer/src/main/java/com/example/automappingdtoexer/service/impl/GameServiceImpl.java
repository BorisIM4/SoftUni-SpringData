package com.example.automappingdtoexer.service.impl;

import com.example.automappingdtoexer.model.dto.GameAddDto;
import com.example.automappingdtoexer.model.entity.Game;
import com.example.automappingdtoexer.repository.GameRepository;
import com.example.automappingdtoexer.service.GameService;
import com.example.automappingdtoexer.service.UserService;
import com.example.automappingdtoexer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public GameServiceImpl(GameRepository gameRepository, UserService userService, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void addGame(GameAddDto gameAddDto) {

        Set<ConstraintViolation<GameAddDto>> violations =
                validationUtil.getViolations(gameAddDto);

        if (!violations.isEmpty()) {
            violations.stream().map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

            return;
        }

        Game game = modelMapper.map(gameAddDto, Game.class);
        game.setReleaseDate(LocalDate.parse(gameAddDto.getReleasedDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        gameRepository.save(game);

        System.out.printf("Added game %s%n" ,gameAddDto.getTitle());
    }

    @Override
    public void editGame(Long gameId, BigDecimal price, Double size) {
        Game game = gameRepository
                .findById(gameId)
                .orElse(null);

        if (game == null) {
            System.out.println("Id is not correct");
            return;
        }
        game.setPrice(price);
//        game.setSize(size);

        gameRepository.save(game);
    }
}

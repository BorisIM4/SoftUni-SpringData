package com.example.automappingdtoexer.config;

import com.example.automappingdtoexer.model.dto.GameAddDto;
import com.example.automappingdtoexer.model.entity.Game;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(GameAddDto.class, Game.class)
                .addMappings(mapper ->
                        mapper.map(GameAddDto::getThumbnailURL,
                                Game::setImageThumbnail));

        Converter<String, LocalDate> localDateConverter = new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(MappingContext<String, LocalDate> mappingContext) {
                return mappingContext.getDestination() == null
                        ? LocalDate.now()
                        : LocalDate.parse(mappingContext.getSource()
                        , DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            }
        };

        modelMapper.addConverter(localDateConverter);

        return modelMapper;
    }
}

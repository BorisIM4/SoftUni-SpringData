package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CitySeedDto;
import softuni.exam.models.entity.City;
import softuni.exam.repository.CityRepository;
import softuni.exam.service.CityService;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

@Service
public class CityServiceImpl implements CityService {

    public static final String CITY_FILE_PATH = "src/main/resources/files/json/cities.json";

    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public CityServiceImpl(CityRepository cityRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Path.of(CITY_FILE_PATH));
    }

    @Override
    public String importCities() throws IOException {
        StringBuilder sb = new StringBuilder();

        CitySeedDto[] citySeedDtos = gson
                .fromJson(readCitiesFileContent(), CitySeedDto[].class);

        Map<String, CitySeedDto> citySeedDtoMap = new LinkedHashMap<>();

        for (CitySeedDto citySeedDto : citySeedDtos) {
            String name = citySeedDto.getCityName();
            citySeedDtoMap.put(name, citySeedDto);
        }

        citySeedDtoMap.forEach((s, citySeedDto) -> {
            boolean isValid = validationUtil.isValid(citySeedDto);

            sb.append(isValid ? String.format("Successfully imported city %s - %d",
                    citySeedDto.getCityName(),
                    citySeedDto.getPopulation())
                    : "Invalid city")
                    .append(System.lineSeparator());
        });

        System.out.println();


        return sb.toString();
    }

}

package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountrySeedDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    private static final String COUNTRY_FILE_PATH = "src/main/resources/files/json/countries.json";

    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CountryServiceImpl(CountryRepository countryRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files
                .readString(Path.of(COUNTRY_FILE_PATH));
    }

    @Override
    public String importCountries() throws IOException {
//        CountrySeedDto[] countrySeedDtos = gson
//                .fromJson(readCountriesFromFile(), CountrySeedDto[].class);

        StringBuilder sb = new StringBuilder();

        CountrySeedDto[] countrySeedDtos = gson
                .fromJson(readCountriesFromFile(), CountrySeedDto[].class);

//        List<Country> countries = Arrays.stream(countrySeedDtos).filter(validationUtil::isValid)
//                .map(countrySeedDto -> modelMapper.map(countrySeedDto, Country.class))
//                .collect(Collectors.toList());

        Arrays.stream(countrySeedDtos)
                .filter(countrySeedDto -> {

                    boolean isValid = validationUtil.isValid(countrySeedDto);
                    String textWithCountryAndCurrency = sb.toString();
                    String nameCountryCurr = countrySeedDto.getCountryName();

                    if (!textWithCountryAndCurrency.contains(nameCountryCurr)
                            || !sb.toString().contains(countrySeedDto.getCurrency())) {
                        sb.append(isValid ? String.format("Successfully imported country %s - %s"
                                , countrySeedDto.getCountryName()
                                , countrySeedDto.getCurrency())
                                : "Invalid country")
                                .append(System.lineSeparator());
                    }

                    return isValid;
                })
                .map(countrySeedDto -> modelMapper.map(countrySeedDto, Country.class))
                .forEach(countryRepository::save);

        return sb.toString();
    }
}

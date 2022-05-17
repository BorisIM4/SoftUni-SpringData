package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountrySeedDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    private static final String COUNTRY_FILE_PATH = "src/main/resources/files/json/countries.json";

    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
//    private final Validator validator;

    public CountryServiceImpl(CountryRepository countryRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
//        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
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

        StringBuilder sb = new StringBuilder();

        CountrySeedDto[] countrySeedDtos = gson
                .fromJson(readCountriesFromFile(), CountrySeedDto[].class);

        Arrays.stream(countrySeedDtos)
                .filter(countrySeedDto -> {
                    boolean isValid = validationUtil.isValid(countrySeedDto);

                    Optional<Country> optionalCountry = this.countryRepository
                            .findByCountryName(countrySeedDto.getCountryName());

                    if (optionalCountry.isEmpty()){
                            sb.append(isValid ? String.format("Successfully imported country %s - %s"
                                    , countrySeedDto.getCountryName()
                                    , countrySeedDto.getCurrency())
                                    : "Invalid country")
                                    .append(System.lineSeparator());

                             isValid = true;

                    } else {
                        sb.append("Invalid country")
                                .append(System.lineSeparator());
                        isValid = false;
                    }
                    return isValid;
                })
                .map(countrySeedDto -> modelMapper.map(countrySeedDto, Country.class))
                .forEach(countryRepository::save);

        return sb.toString();

    }
}

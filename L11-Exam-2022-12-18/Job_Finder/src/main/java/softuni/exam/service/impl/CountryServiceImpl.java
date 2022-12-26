package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountrySeedDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CountryServiceImpl implements CountryService {
    public static final String COUNTRY_FILE_PATH = "src/main/resources/files/json/countries.json";

    private final CountryRepository countryRepository;
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;

        this.gson = new GsonBuilder().create();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFileContent() throws IOException {
        return Files.readString(Path.of(COUNTRY_FILE_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        String json = this.readCountriesFileContent();

        List<String> result = new ArrayList<>();

        CountrySeedDto[] countrySeedDtos = this.gson.fromJson(json, CountrySeedDto[].class);
        for (CountrySeedDto countrySeedDto : countrySeedDtos) {
            Set<ConstraintViolation<CountrySeedDto>> validationErrors = this.validator.validate(countrySeedDto);

            if (validationErrors.isEmpty()) {
                Country country = this.modelMapper.map(countrySeedDto, Country.class);
                this.countryRepository.save(country);

                String message = String.format("Successfully imported country %s - %s"
                        , countrySeedDto.getName()
                        , countrySeedDto.getCode());
                result.add(message);

            } else {
                result.add("Invalid country");
            }
        }

        return String.join("\n", result);
    }
}

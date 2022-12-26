package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountrySeedDto;
import softuni.exam.models.dto.PersonSeedDto;
import softuni.exam.models.entity.Person;
import softuni.exam.repository.PersonRepository;
import softuni.exam.service.PersonService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonServiceImpl implements PersonService {
    public static final String PERSON_FILE_PATH = "src/main/resources/files/json/people.json";

    private final PersonRepository personRepository;
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;

        this.gson = new GsonBuilder().create();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.personRepository.count() > 0;
    }

    @Override
    public String readPeopleFromFile() throws IOException {
        return Files.readString(Path.of(PERSON_FILE_PATH));
    }

    @Override
    public String importPeople() throws IOException, JAXBException {
        String json = this.readPeopleFromFile();

        List<String> result = new ArrayList<>();

        PersonSeedDto[] personSeedDtos = this.gson.fromJson(json, PersonSeedDto[].class);
        for (PersonSeedDto personSeedDto : personSeedDtos) {
            Set<ConstraintViolation<PersonSeedDto>> validationErrors = this.validator.validate(personSeedDto);

            if (validationErrors.isEmpty()) {
                Optional<Person> optPersonFirstName = this.personRepository.findByFirstName(personSeedDto.getFirstName());
                Optional<Person> optPersonEmail = this.personRepository.findByEmail(personSeedDto.getEmail());
                Optional<Person> optPersonPhone = this.personRepository.findByPhone(personSeedDto.getPhone());

                if (optPersonFirstName.isEmpty() && optPersonEmail.isEmpty() && optPersonPhone.isEmpty()){
                    Person person = this.modelMapper.map(personSeedDto, Person.class);
                    this.personRepository.save(person);

                    String message = String.format("Successfully imported person %s %s"
                    ,personSeedDto.getFirstName()
                    ,personSeedDto.getLastName());
                    result.add(message);
                } else {
                    result.add("Invalid person");
                }

            } else {
                result.add("Invalid person");
            }

        }

        return String.join("\n", result);
    }
}

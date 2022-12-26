package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CompanyRootSeedDto;
import softuni.exam.models.dto.CompanySeedDto;
import softuni.exam.models.entity.Company;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CompanyService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {
    public static final String COMPANY_FILE_PATH = "src/main/resources/files/xml/companies.xml";

    private final CompanyRepository companyRepository;
    private final CountryRepository countryRepository;

    private final ModelMapper modelMapper;
    private final Validator validator;
    private final Unmarshaller unmarshaller;

    public CompanyServiceImpl(CompanyRepository companyRepository, CountryRepository countryRepository) throws JAXBException {
        this.companyRepository = companyRepository;
        this.countryRepository = countryRepository;

        JAXBContext context = JAXBContext.newInstance(CompanyRootSeedDto.class);
        this.unmarshaller = context.createUnmarshaller();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();

        this.modelMapper.addConverter(ctx -> LocalDate.parse(ctx.getSource(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                String.class, LocalDate.class);
    }

    @Override
    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesFromFile() throws IOException {
        return Files.readString(Path.of(COMPANY_FILE_PATH));
    }

    @Override
    public String importCompanies() throws IOException, JAXBException {
        CompanyRootSeedDto compSeedDto = (CompanyRootSeedDto) this.unmarshaller.unmarshal(new FileReader(COMPANY_FILE_PATH));

        return compSeedDto
                .getCompanies()
                .stream()
                .map(this::importCompany)
                .collect(Collectors.joining("\n"));
    }


    private String importCompany(CompanySeedDto companySeedDto) {
        Set<ConstraintViolation<CompanySeedDto>> errors = this.validator.validate(companySeedDto);

        if (!errors.isEmpty()) {
            return "Invalid company";
        }

        Optional<Company> optCompany = this.companyRepository.findByName(companySeedDto.getName());
        if (optCompany.isPresent()) {
            return "Invalid company";
        }

        long countryId = companySeedDto.getCountryId();

        Optional<Country> country = this.countryRepository.findById(countryId);

        Company company = this.modelMapper.map(companySeedDto, Company.class);
        company.setCountry(country.get());


        this.companyRepository.save(company);

        return "Successfully imported company " + company;
    }
}

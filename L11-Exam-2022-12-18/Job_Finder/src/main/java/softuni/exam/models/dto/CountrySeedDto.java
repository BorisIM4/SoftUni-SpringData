package softuni.exam.models.dto;

import javax.validation.constraints.Size;

public class CountrySeedDto {
    private String name;
    private String countryCode;
    private String currency;

    public CountrySeedDto() {
    }

    @Size(min = 2, max = 30)
    public String getName() {
        return name;
    }

    @Size(min = 2, max = 19)
    public String getCode() {
        return countryCode;
    }

    @Size(min = 2, max = 19)
    public String getCurrency() {
        return currency;
    }
}

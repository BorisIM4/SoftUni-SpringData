package softuni.exam.models.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PersonSeedDto {
    //    "email": "lrann0@t-online.de",
    //    "firstName": "Lorna",
    //    "lastName": "Rann",
    //    "phone": "462-463-0432",
    //    "statusType": "freelancer",
    //    "country": "21"

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String statusType;
    private Long country;

    public PersonSeedDto() {
    }

    @Size(min = 2, max = 30)
    public String getFirstName() {
        return firstName;
    }

    @Size(min = 2, max = 30)
    public String getLastName() {
        return lastName;
    }

    @Email
    public String getEmail() {
        return email;
    }

    @Size(min = 2, max = 13)
    public String getPhone() {
        return phone;
    }

    @NotNull
    public String getStatusType() {
        return statusType;
    }

    public Long getCountry() {
        return country;
    }
}

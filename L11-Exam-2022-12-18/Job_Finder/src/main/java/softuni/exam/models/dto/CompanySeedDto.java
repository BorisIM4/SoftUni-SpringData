package softuni.exam.models.dto;

import softuni.exam.models.entity.Country;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CompanySeedDto {

    @XmlElement(name = "companyName")
    private String name;

    @XmlElement
    private String dateEstablished;

    @XmlElement
    private String website;

    @XmlElement
    private Long countryId;


    @Size(min = 2, max = 40)
    public String getName() {
        return name;
    }


    public String getDateEstablished() {
        return dateEstablished;
    }

    @Size(min = 2, max = 30)
    public String getWebsite() {
        return website;
    }

    public Long getCountryId() {
        return countryId;
    }
}

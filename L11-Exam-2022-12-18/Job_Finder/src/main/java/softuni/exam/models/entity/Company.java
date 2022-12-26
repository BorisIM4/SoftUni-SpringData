package softuni.exam.models.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "companies")
public class Company extends BaseEntity{

    private String name;
    private String website;
    private LocalDate dateEstablished;
    private Country countryId;
    private Set<Job> jobs;

    public Company() {
    }

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Column
    public LocalDate getDateEstablished() {
        return dateEstablished;
    }

    public void setDateEstablished(LocalDate dateEstablished) {
        this.dateEstablished = dateEstablished;
    }

    @ManyToOne
    public Country getCountry() {
        return countryId;
    }

    public void setCountry(Country country) {
        this.countryId = country;
    }

    @ManyToMany
    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    @Override
    public String toString() {
        return name + " - " +  countryId.getId();
    }
}

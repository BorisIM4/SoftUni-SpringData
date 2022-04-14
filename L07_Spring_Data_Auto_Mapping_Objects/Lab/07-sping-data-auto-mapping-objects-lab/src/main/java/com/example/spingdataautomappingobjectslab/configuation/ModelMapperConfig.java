package com.example.spingdataautomappingobjectslab.configuation;

import com.example.spingdataautomappingobjectslab.models.dto.EmployeeDto;
import com.example.spingdataautomappingobjectslab.models.entities.Employee;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Employee, EmployeeDto>() {
            @Override
            protected void configure() {
                map().setIncome(source.getSalary());
            }
        });



        return modelMapper;
    }
}

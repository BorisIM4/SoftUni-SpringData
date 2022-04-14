package com.example.spingdataautomappingobjectslab.services;

import com.example.spingdataautomappingobjectslab.models.dto.EmployeeDto;
import com.example.spingdataautomappingobjectslab.models.dto.ManagerDto;
import com.example.spingdataautomappingobjectslab.models.entities.Employee;
import com.example.spingdataautomappingobjectslab.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public ManagerDto findOne(Long id) {

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(
                this.employeeRepository.findById(id).orElseThrow(),
                ManagerDto.class
        );
    }
}

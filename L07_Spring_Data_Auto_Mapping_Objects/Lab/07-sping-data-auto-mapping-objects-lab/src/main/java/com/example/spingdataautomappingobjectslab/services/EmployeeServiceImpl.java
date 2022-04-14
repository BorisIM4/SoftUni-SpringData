package com.example.spingdataautomappingobjectslab.services;

import com.example.spingdataautomappingobjectslab.models.dto.ManagerDto;
import com.example.spingdataautomappingobjectslab.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ManagerDto findOne(Long id) {

        return modelMapper.map(
                this.employeeRepository.findById(id).orElseThrow(),
                ManagerDto.class
        );
    }

    @Override
    public List<ManagerDto> findAll() {

        return modelMapper.map(
                this.employeeRepository.findAll(),
                new TypeToken<List<ManagerDto>>() {}.getType()
        );
    }

}

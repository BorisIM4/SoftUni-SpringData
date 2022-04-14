package com.example.spingdataautomappingobjectslab.services;

import com.example.spingdataautomappingobjectslab.models.dto.EmployeeDto;
import com.example.spingdataautomappingobjectslab.models.dto.ManagerDto;

import java.util.List;

public interface EmployeeService {

    ManagerDto findOne(Long id);

    List<ManagerDto> findAll();
}

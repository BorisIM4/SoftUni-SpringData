package com.example.spingdataautomappingobjectslab.services;

import com.example.spingdataautomappingobjectslab.models.dto.EmployeeDto;
import com.example.spingdataautomappingobjectslab.models.dto.ManagerDto;

public interface EmployeeService {

    ManagerDto findOne(Long id);
}

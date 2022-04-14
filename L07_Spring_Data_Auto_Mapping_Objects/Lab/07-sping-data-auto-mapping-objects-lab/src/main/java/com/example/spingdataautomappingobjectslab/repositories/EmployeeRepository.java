package com.example.spingdataautomappingobjectslab.repositories;

import com.example.spingdataautomappingobjectslab.models.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {


}

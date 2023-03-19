package com.tronina.dashbord.repository;

import com.tronina.dashbord.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Override
    List findAll();
}

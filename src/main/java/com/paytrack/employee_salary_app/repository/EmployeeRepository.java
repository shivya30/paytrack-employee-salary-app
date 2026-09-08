package com.paytrack.employee_salary_app.repository;

import com.paytrack.employee_salary_app.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByNameContainingIgnoreCase(String name);

    List<Employee> findByCountryIgnoreCase(String country);

    List<Employee> findByDepartmentIgnoreCase(String department);

    List<Employee> findByCountryIgnoreCaseAndDepartmentIgnoreCase(
            String country,
            String department
    );
}
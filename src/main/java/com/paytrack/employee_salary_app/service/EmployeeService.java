package com.paytrack.employee_salary_app.service;

import com.paytrack.employee_salary_app.entity.Employee;
import com.paytrack.employee_salary_app.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getEmployees(
            String search,
            String country,
            String department) {

        if (search != null && !search.isBlank()) {
            return employeeRepository.findByNameContainingIgnoreCase(search);
        }

        if (country != null && department != null) {
            return employeeRepository
                    .findByCountryIgnoreCaseAndDepartmentIgnoreCase(country, department);
        }

        if (country != null) {
            return employeeRepository.findByCountryIgnoreCase(country);
        }

        if (department != null) {
            return employeeRepository.findByDepartmentIgnoreCase(department);
        }

        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateSalary(Long id, BigDecimal salary) {
        Employee employee = getEmployeeById(id);
        employee.setSalary(salary);
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found with id: " + id);
        }

        employeeRepository.deleteById(id);
    }
}
package com.paytrack.employee_salary_app.service;

import com.paytrack.employee_salary_app.entity.Employee;
import com.paytrack.employee_salary_app.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private final EmployeeRepository employeeRepository;

    public DashboardService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Map<String, Object> getDashboard() {

        List<Employee> employees = employeeRepository.findAll();

        long totalEmployees = employees.size();

        BigDecimal totalPayroll = employees.stream()
                .map(Employee::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal averageSalary = totalEmployees == 0
                ? BigDecimal.ZERO
                : totalPayroll.divide(
                        BigDecimal.valueOf(totalEmployees),
                        2,
                        java.math.RoundingMode.HALF_UP
                );

        Map<String, Object> dashboard = new HashMap<>();

        dashboard.put("totalEmployees", totalEmployees);
        dashboard.put("totalPayroll", totalPayroll);
        dashboard.put("averageSalary", averageSalary);

        return dashboard;
    }
}
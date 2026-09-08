package com.paytrack.employee_salary_app.service;

import com.paytrack.employee_salary_app.entity.Employee;
import com.paytrack.employee_salary_app.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private final EmployeeRepository employeeRepository;

    public AnalyticsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Map<String, Object> getAnalytics() {

        List<Employee> employees = employeeRepository.findAll();

        Map<String, Object> result = new LinkedHashMap<>();

        if (employees.isEmpty()) {
            result.put("message", "No employee data available");
            return result;
        }

        // Highest salary
        Employee highest = employees.stream()
                .max(Comparator.comparing(Employee::getSalary))
                .orElse(null);

        // Lowest salary
        Employee lowest = employees.stream()
                .min(Comparator.comparing(Employee::getSalary))
                .orElse(null);

        // Average salary
        BigDecimal totalSalary = employees.stream()
                .map(Employee::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal averageSalary = totalSalary.divide(
                BigDecimal.valueOf(employees.size()),
                2,
                java.math.RoundingMode.HALF_UP
        );

        // Median salary
        List<BigDecimal> salaries = employees.stream()
                .map(Employee::getSalary)
                .sorted()
                .toList();

        int middle = salaries.size() / 2;

        BigDecimal medianSalary;

        if (salaries.size() % 2 == 0) {
            medianSalary = salaries.get(middle - 1)
                    .add(salaries.get(middle))
                    .divide(BigDecimal.valueOf(2), 2,
                            java.math.RoundingMode.HALF_UP);
        } else {
            medianSalary = salaries.get(middle);
        }

        // Salary ranges
        Map<String, Long> salaryRanges = new LinkedHashMap<>();

        salaryRanges.put(
                "Below 50K",
                employees.stream()
                        .filter(e -> e.getSalary().compareTo(BigDecimal.valueOf(50000)) < 0)
                        .count()
        );

        salaryRanges.put(
                "50K - 100K",
                employees.stream()
                        .filter(e -> e.getSalary().compareTo(BigDecimal.valueOf(50000)) >= 0)
                        .filter(e -> e.getSalary().compareTo(BigDecimal.valueOf(100000)) <= 0)
                        .count()
        );

        salaryRanges.put(
                "100K - 150K",
                employees.stream()
                        .filter(e -> e.getSalary().compareTo(BigDecimal.valueOf(100000)) > 0)
                        .filter(e -> e.getSalary().compareTo(BigDecimal.valueOf(150000)) <= 0)
                        .count()
        );

        salaryRanges.put(
                "Above 150K",
                employees.stream()
                        .filter(e -> e.getSalary().compareTo(BigDecimal.valueOf(150000)) > 0)
                        .count()
        );

        // Payroll by country
        Map<String, BigDecimal> payrollByCountry = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getCountry,
                        LinkedHashMap::new,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                Employee::getSalary,
                                BigDecimal::add
                        )
                ));

        // Payroll by department
        Map<String, BigDecimal> payrollByDepartment = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        LinkedHashMap::new,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                Employee::getSalary,
                                BigDecimal::add
                        )
                ));

        result.put("totalEmployees", employees.size());
        result.put("averageSalary", averageSalary);
        result.put("medianSalary", medianSalary);

        result.put("highestSalary",
                highest != null ? highest.getSalary() : BigDecimal.ZERO);

        result.put("lowestSalary",
                lowest != null ? lowest.getSalary() : BigDecimal.ZERO);

        result.put("salaryRanges", salaryRanges);
        result.put("payrollByCountry", payrollByCountry);
        result.put("payrollByDepartment", payrollByDepartment);

        return result;
    }
}
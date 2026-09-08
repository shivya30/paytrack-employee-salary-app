package com.paytrack.employee_salary_app;

import com.paytrack.employee_salary_app.entity.Employee;
import com.paytrack.employee_salary_app.repository.EmployeeRepository;
import com.paytrack.employee_salary_app.service.AnalyticsService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnalyticsServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private AnalyticsService analyticsService;

    @Test
    void shouldCalculateSalaryAnalytics() {

        Employee e1 = new Employee(
                "EMP00001",
                "A",
                "India",
                "IT",
                new BigDecimal("40000"),
                "INR"
        );

        Employee e2 = new Employee(
                "EMP00002",
                "B",
                "India",
                "Finance",
                new BigDecimal("80000"),
                "INR"
        );

        Employee e3 = new Employee(
                "EMP00003",
                "C",
                "USA",
                "IT",
                new BigDecimal("120000"),
                "USD"
        );

        when(employeeRepository.findAll())
                .thenReturn(List.of(e1, e2, e3));

        Map<String, Object> result =
                analyticsService.getAnalytics();

        assertEquals(
                3,
                result.get("totalEmployees")
        );

        assertEquals(
                new BigDecimal("80000.00"),
                result.get("averageSalary")
        );

        assertEquals(
                new BigDecimal("80000"),
                result.get("medianSalary")
        );

        assertEquals(
                new BigDecimal("120000"),
                result.get("highestSalary")
        );

        assertEquals(
                new BigDecimal("40000"),
                result.get("lowestSalary")
        );
    }

    @Test
    void shouldHandleEmptyEmployeeList() {

        when(employeeRepository.findAll())
                .thenReturn(List.of());

        Map<String, Object> result =
                analyticsService.getAnalytics();

        assertEquals(
                "No employee data available",
                result.get("message")
        );
    }
}
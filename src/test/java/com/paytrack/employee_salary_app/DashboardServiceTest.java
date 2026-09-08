package com.paytrack.employee_salary_app;

import com.paytrack.employee_salary_app.entity.Employee;
import com.paytrack.employee_salary_app.repository.EmployeeRepository;
import com.paytrack.employee_salary_app.service.DashboardService;

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
class DashboardServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private DashboardService dashboardService;

    @Test
    void shouldCalculateDashboardMetrics() {

        Employee e1 = new Employee(
                "EMP00001",
                "Rahul",
                "India",
                "IT",
                new BigDecimal("60000"),
                "INR"
        );

        Employee e2 = new Employee(
                "EMP00002",
                "Priya",
                "India",
                "HR",
                new BigDecimal("80000"),
                "INR"
        );

        when(employeeRepository.findAll())
                .thenReturn(List.of(e1, e2));

        Map<String, Object> result =
                dashboardService.getDashboard();

        assertEquals(
                2L,
                result.get("totalEmployees")
        );

        assertEquals(
                new BigDecimal("140000"),
                result.get("totalPayroll")
        );

        assertEquals(
                new BigDecimal("70000.00"),
                result.get("averageSalary")
        );
    }

    @Test
    void shouldReturnZeroForEmptyDatabase() {

        when(employeeRepository.findAll())
                .thenReturn(List.of());

        Map<String, Object> result =
                dashboardService.getDashboard();

        assertEquals(
                0L,
                result.get("totalEmployees")
        );

        assertEquals(
                BigDecimal.ZERO,
                result.get("totalPayroll")
        );

        assertEquals(
                BigDecimal.ZERO,
                result.get("averageSalary")
        );
    }
}
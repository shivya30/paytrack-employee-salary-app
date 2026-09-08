package com.paytrack.employee_salary_app;

import com.paytrack.employee_salary_app.entity.Employee;
import com.paytrack.employee_salary_app.repository.EmployeeRepository;
import com.paytrack.employee_salary_app.service.EmployeeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee(
                "EMP00001",
                "Rahul Sharma",
                "India",
                "IT",
                new BigDecimal("75000"),
                "INR"
        );
    }

    @Test
    void shouldReturnAllEmployees() {
        when(employeeRepository.findAll())
                .thenReturn(List.of(employee));

        List<Employee> result =
                employeeService.getEmployees(null, null, null);

        assertEquals(1, result.size());
        assertEquals("EMP00001", result.get(0).getEmployeeCode());
    }

    @Test
    void shouldSearchEmployeesByName() {
        when(employeeRepository.findByNameContainingIgnoreCase("rahul"))
                .thenReturn(List.of(employee));

        List<Employee> result =
                employeeService.getEmployees("rahul", null, null);

        assertEquals(1, result.size());
        assertEquals("Rahul Sharma", result.get(0).getName());

        verify(employeeRepository)
                .findByNameContainingIgnoreCase("rahul");
    }

    @Test
    void shouldFilterByCountry() {
        when(employeeRepository.findByCountryIgnoreCase("India"))
                .thenReturn(List.of(employee));

        List<Employee> result =
                employeeService.getEmployees(null, "India", null);

        assertEquals(1, result.size());
        assertEquals("India", result.get(0).getCountry());
    }

    @Test
    void shouldFilterByDepartment() {
        when(employeeRepository.findByDepartmentIgnoreCase("IT"))
                .thenReturn(List.of(employee));

        List<Employee> result =
                employeeService.getEmployees(null, null, "IT");

        assertEquals(1, result.size());
        assertEquals("IT", result.get(0).getDepartment());
    }

    @Test
    void shouldGetEmployeeById() {
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        Employee result =
                employeeService.getEmployeeById(1L);

        assertEquals("EMP00001", result.getEmployeeCode());
    }

    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() {
        when(employeeRepository.findById(999L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> employeeService.getEmployeeById(999L)
        );

        assertEquals(
                "Employee not found with id: 999",
                exception.getMessage()
        );
    }

    @Test
    void shouldCreateEmployee() {
        when(employeeRepository.save(employee))
                .thenReturn(employee);

        Employee result =
                employeeService.createEmployee(employee);

        assertNotNull(result);
        assertEquals(
                "EMP00001",
                result.getEmployeeCode()
        );

        verify(employeeRepository).save(employee);
    }

    @Test
    void shouldUpdateSalary() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(employeeRepository.save(employee))
                .thenReturn(employee);

        Employee result =
                employeeService.updateSalary(
                        1L,
                        new BigDecimal("90000")
                );

        assertEquals(
                new BigDecimal("90000"),
                result.getSalary()
        );

        verify(employeeRepository).save(employee);
    }

    @Test
    void shouldDeleteEmployee() {

        when(employeeRepository.existsById(1L))
                .thenReturn(true);

        employeeService.deleteEmployee(1L);

        verify(employeeRepository)
                .deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingMissingEmployee() {

        when(employeeRepository.existsById(999L))
                .thenReturn(false);

        assertThrows(
                RuntimeException.class,
                () -> employeeService.deleteEmployee(999L)
        );

        verify(employeeRepository, never())
                .deleteById(anyLong());
    }
}
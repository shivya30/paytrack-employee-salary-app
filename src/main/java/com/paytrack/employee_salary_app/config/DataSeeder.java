package com.paytrack.employee_salary_app.config;

import com.paytrack.employee_salary_app.entity.Employee;
import com.paytrack.employee_salary_app.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner loadEmployees(EmployeeRepository repository) {

        return args -> {

            // Don't insert duplicate data every time the application starts
            if (repository.count() > 0) {
                System.out.println("Employees already exist. Skipping data seeding.");
                return;
            }

            String[] firstNames = {
                    "Rahul", "Amit", "Priya", "Neha", "Arjun",
                    "Rohit", "Sneha", "Ankit", "Pooja", "Vikas",
                    "Karan", "Riya", "Aditya", "Simran", "Nikhil"
            };

            String[] lastNames = {
                    "Sharma", "Verma", "Singh", "Kumar", "Gupta",
                    "Mehta", "Patel", "Agarwal", "Joshi", "Malhotra"
            };

            String[] countries = {
                    "India", "USA", "UK", "Canada", "Australia"
            };

            String[] departments = {
                    "IT", "Finance", "HR", "Marketing",
                    "Sales", "Operations", "Engineering"
            };

            Random random = new Random();

            List<Employee> employees = new ArrayList<>();

            for (int i = 1; i <= 10000; i++) {

                String name = firstNames[random.nextInt(firstNames.length)]
                        + " "
                        + lastNames[random.nextInt(lastNames.length)];

                String country = countries[random.nextInt(countries.length)];
                String department = departments[random.nextInt(departments.length)];

                BigDecimal salary = BigDecimal.valueOf(
                        30000 + random.nextInt(170001)
                );

                Employee employee = new Employee(
                        String.format("EMP%05d", i),
                        name,
                        country,
                        department,
                        salary,
                        "INR"
                );

                employees.add(employee);
            }

            repository.saveAll(employees);

            System.out.println("======================================");
            System.out.println("10,000 employees inserted successfully!");
            System.out.println("======================================");
        };
    }
}
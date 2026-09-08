# PayTrack — Employee Salary Management & Analytics

## Project Overview
PayTrack is a Spring Boot web application for managing employee salary records and providing payroll/salary analytics for a dataset of approximately 10,000 employees.

## Functional Requirements
- Dashboard: total employees, total payroll, average salary, median salary.
- Employee search by name.
- Filter employees by country and department.
- View employee salary/details.
- Add employee.
- Update employee salary.
- Delete employee.
- Salary analytics: average, median, highest, lowest, salary ranges.
- Payroll grouped by country and department.
- Store a three-letter currency code with each employee.
- Expose REST APIs for dashboard, employees and analytics.

## Non-Functional Requirements
- Java 21 and Spring Boot.
- MySQL relational database.
- Layered architecture: Controller → Service → Repository → Database.
- Support approximately 10,000 employee records.
- Avoid rendering all 10,000 rows in the browser.
- Validate input and handle missing employees.
- Do not commit database passwords or other secrets.

## REST API
| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/dashboard` | Dashboard KPIs |
| GET | `/api/employees` | List/search/filter employees |
| GET | `/api/employees/{id}` | Get employee |
| POST | `/api/employees` | Add employee |
| PUT | `/api/employees/{id}/salary` | Update salary |
| DELETE | `/api/employees/{id}` | Delete employee |
| GET | `/api/analytics` | Salary analytics |

## Data Model
Employee fields:
- id
- employeeCode (unique)
- name
- country
- department
- salary
- currency

## Currency Note
The current development dataset uses INR. A production multi-currency payroll system must convert salaries to a selected/base currency before combining different currencies.

## Acceptance Criteria
The application starts successfully, connects to MySQL, loads employee data, supports employee CRUD/search/filtering, displays dashboard KPIs and analytics, and exposes the documented REST APIs.

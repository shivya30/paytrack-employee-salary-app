# JUnit Test Cases — PayTrack

## Testing Approach

PayTrack uses JUnit 5 and Mockito for service-layer unit testing.

Repository dependencies are mocked so the tests verify business logic without requiring a live MySQL database.

## Test Classes

### EmployeeServiceTest

Tests:

- Return all employees.
- Search employees by name.
- Filter by country.
- Filter by department.
- Get employee by ID.
- Handle employee-not-found.
- Create employee.
- Update salary.
- Delete employee.
- Handle deletion of a missing employee.

### DashboardServiceTest

Tests:

- Total employee calculation.
- Total payroll calculation.
- Average salary calculation.
- Empty database handling.

### AnalyticsServiceTest

Tests:

- Total employees.
- Average salary.
- Median salary.
- Highest salary.
- Lowest salary.
- Empty employee list handling.

## Run Tests

From the project root:

```powershell
.\mvnw.cmd test
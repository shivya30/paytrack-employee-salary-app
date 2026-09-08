# How to Run PayTrack

## Prerequisites
- JDK 21
- MySQL
- Git
- VS Code or another Java IDE

Check Java:
```powershell
java -version
```

Check Maven Wrapper:
```powershell
.\mvnw.cmd -version
```

## 1. Create MySQL Database
In MySQL Workbench:

```sql
CREATE DATABASE employee_salary_db;
```

## 2. Configure Database
Open `src/main/resources/application.properties`.

Example:

```properties
spring.application.name=employee-salary-app
spring.datasource.url=jdbc:mysql://localhost:3306/employee_salary_db
spring.datasource.username=root
spring.datasource.password=YOUR_LOCAL_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Never commit a real password to GitHub.

## 3. Build
From the project root:

```powershell
.\mvnw.cmd clean compile
```

## 4. Run
```powershell
.\mvnw.cmd spring-boot:run
```

Or run `EmployeeSalaryAppApplication.java` from VS Code.

## 5. Open
Open:

`http://localhost:8080`

## 6. Development Data
When the database has no employees, the application seeds 10,000 employee records. If records already exist, seeding is skipped.

## 7. API Checks
- `GET /api/dashboard`
- `GET /api/employees`
- `GET /api/employees/1`
- `GET /api/analytics`

## 8. Stop
Press `Ctrl + C` in the running terminal.

## Troubleshooting
- MySQL error: verify MySQL is running, database name, credentials and port 3306.
- Port 8080 busy: stop the other application or configure another port.
- Old CSS/JS: press `Ctrl + Shift + R`.

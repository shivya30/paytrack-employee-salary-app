# PayTrack Architecture

## Architecture Diagram

```mermaid
flowchart LR
    U[User / Browser] --> UI[HTML CSS JavaScript]
    UI --> C[Spring Boot REST Controllers]
    C --> S[Service Layer]
    S --> R[Spring Data JPA Repository]
    R --> DB[(MySQL Database)]
    S --> A[Dashboard & Analytics Logic]
    A --> R
```

## Request Flow

```mermaid
sequenceDiagram
    participant U as User
    participant UI as Web UI
    participant C as REST Controller
    participant S as Service
    participant R as Repository
    participant DB as MySQL

    U->>UI: Search / Add / Edit / Delete
    UI->>C: HTTP request
    C->>S: Business operation
    S->>R: Repository call
    R->>DB: JPA/Hibernate query
    DB-->>R: Data
    R-->>S: Result
    S-->>C: Result
    C-->>UI: JSON
    UI-->>U: Updated screen
```

## Layers

### Controller
Package: `com.paytrack.employee_salary_app.controller`

Defines REST endpoints and delegates operations to services.

### Service
Package: `com.paytrack.employee_salary_app.service`

Contains employee operations, dashboard calculations and salary analytics.

### Repository
Package: `com.paytrack.employee_salary_app.repository`

Uses Spring Data JPA for database access and employee search/filter queries.

### Entity
Package: `com.paytrack.employee_salary_app.entity`

Contains the `Employee` JPA entity mapped to the `employees` table.

### Configuration
Package: `com.paytrack.employee_salary_app.config`

Contains data seeding and Spring Security configuration.

## Frontend
The current frontend uses `index.html`, `style.css`, and `app.js` under `src/main/resources/static/`.

## Performance Design
The UI displays only the first 50 employee records to avoid creating 10,000 DOM rows. For production, use backend pagination with Spring Data `Pageable`, database indexes, DTOs and SQL-side aggregation.

## Security
The current local development configuration permits API requests for easier testing. Production should use real authentication/authorization, environment-based secrets and hardened security settings.

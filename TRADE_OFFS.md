# Architecture & Technology Trade-offs

## HTML/CSS/JavaScript vs Angular
HTML/CSS/JavaScript was chosen because the current application is small and backend-focused. It reduces frontend complexity. Angular would be a stronger choice for a larger component-based frontend.

## Spring Data JPA vs JDBC
JPA reduces database boilerplate and integrates naturally with Spring Boot. JDBC gives more direct SQL control but requires more manual mapping and code.

## MySQL vs NoSQL
MySQL fits structured employee salary data, relational constraints, filtering and aggregation. NoSQL would be more useful for flexible document-oriented workloads.

## Java Analytics vs SQL Aggregation
The current analytics implementation is simple and easy to understand. For production, large aggregations should move to database-side SQL queries so unnecessary rows are not loaded into application memory.

## First-50 UI vs Pagination
The current UI displays the first 50 employees to avoid rendering 10,000 DOM rows. Production should use server-side pagination with `Pageable`.

## Currency
Each employee stores salary and currency. The current development dataset uses INR. A true multi-currency payroll system should normalize amounts using controlled exchange rates before calculating combined payroll.

## Security
Local development permits API access to simplify testing. Production requires authentication, authorization, secret management and hardened security settings.

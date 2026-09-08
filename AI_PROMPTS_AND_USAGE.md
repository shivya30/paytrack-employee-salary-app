# AI Prompts & Usage

## Purpose
AI was used as a development assistant for architecture planning, debugging, documentation, code review and test-case ideas. The application itself does not depend on an LLM for employee CRUD or salary analytics.

## Architecture Prompt
> Design a simple layered Spring Boot architecture for an employee salary application using Java, MySQL, Spring Data JPA, REST APIs and HTML/CSS/JavaScript. Support 10,000 employees, CRUD, search/filtering, dashboard KPIs and salary analytics. Explain trade-offs.

## Database Prompt
> Design a relational employee salary table containing employee code, name, country, department, salary and currency. Recommend suitable keys, data types and indexes.

## REST API Prompt
> Define REST APIs for employee CRUD, employee search/filtering, dashboard KPIs and salary analytics using clear HTTP methods and resource-oriented endpoints.

## Debugging Prompt
> Analyze this Spring Boot error, identify the root cause, and recommend the smallest safe fix without changing unrelated functionality.

## Frontend Debugging Prompt
> Review HTML, CSS and JavaScript for Add Employee and Edit Salary modal dialogs. Ensure the forms appear as centered overlays rather than inline content. Check CSS conflicts, duplicate functions and DOM ID mismatches.

## Performance Prompt
> Review a Spring Boot application that loads 10,000 employee records into a browser table. Explain the performance issue and propose backend pagination, database filtering and aggregation.

## Interview Prompt
> Act as an interviewer for this employee salary management project. Ask questions about Spring Boot architecture, REST APIs, JPA, MySQL, performance, security, salary analytics, currency handling and trade-offs. Give concise answers based on the implemented project.

## AI Usage Process
1. Define the requirement.
2. Ask AI for an approach.
3. Compare the suggestion with the existing code.
4. Implement the smallest suitable change.
5. Build and run the application.
6. Test the feature.
7. Review the result.

## Interview Statement
“I used AI as a development and review assistant for architecture planning, debugging, documentation and test-case ideas. I validated suggestions by implementing them, running the application and testing the APIs and UI.”

## Important Limitation
Do not claim that an LLM is part of the runtime application unless an actual LLM integration has been implemented.

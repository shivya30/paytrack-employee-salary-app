# Performance Considerations

## Dataset
The development application works with 10,000 employee records.

## Browser
Rendering 10,000 table rows creates a large DOM and can make the browser slow. The current frontend displays only the first 50 records.

## Production Improvements
1. Use Spring Data `Pageable`.
2. Filter in the database.
3. Add indexes to frequently filtered columns such as country, department and name where appropriate.
4. Use SQL aggregate queries for dashboard/analytics.
5. Return DTOs instead of exposing persistence entities directly.
6. Avoid N+1 query patterns.
7. Consider caching expensive analytics where appropriate.

## Preferred Flow
```text
Browser -> requested page/filter -> Database -> required rows -> Browser
```

For analytics:
```text
Browser -> API -> SQL aggregate -> small result -> Browser
```

# Battery Registry Service

This is a microservice which act as battery registry. This is capable of registering batteries. This allows querying battery details based on various filter criteria

# Component Summary
- Type of component:
    - REST API

- Technologies:
    - Platform/runtime: Oracle Java - 11
    - Framework: Spring Boot
    - Database: H2 (in memory database)

# Local environment setup [without Docker]

- Run following command to build and start the application

  ``./gradlew build``

  ``./gradlew bootrun``
- Access the Swagger UI by ``http://localhost:8080/swagger-ui/index.html#``

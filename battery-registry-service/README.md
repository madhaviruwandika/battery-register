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

- Navigate to the folder `battery-registry-service` if you are in the project folder
- Run following command to build and start the application

  ``./gradlew build``

  ``./gradlew bootrun``
- Access the Swagger UI by ``http://localhost:8080/swagger-ui/index.html#``

# API Documentation
- Swagger 3 is integrated for API documentation
- URL : http://localhost:8080/swagger-ui/index.html

# Endpoints

1. Register Batteries
   - Endpoint URI: `/battery/register`
   - Method : `POST`
   - Request Body Sample
       ```
       [
         {
           "name": "test 1",
           "postCode": 2144,
           "wattCapacity": 100
         }
       ]
       ```
   - Response Body Sample
       ```
       {
          "data": {
              "status" : "Success"
          },
          "errors": null,
          "meta": null,
          "hasError": false
       } 
       ```

2. Fetch batteries with search criteria (between post code range, order alphabetically. Order by watt capacity )

   - Endpoint URI: `battery/post-codes`
   - Method : `GET`
   - Request Body Sample
       ```
       curl -X 'GET' 'http://localhost:8080/battery/post-codes?postCodeFrom=1000&postCodeTo=1001&sortBy=NAME&sortOrder=ASC' -H 'accept: application/json'
       ```
   - Response Body Sample
     ```
        {
            "data": [
                {
                    "id": 1,
                    "name": "test",
                    "postCode": 2145,
                    "wattCapacity": 50,
                    "createdAt": "2023-08-26T03:14:10.834Z",
                    "modifiedAt": "2023-08-26T03:14:10.834Z"
                }
            ],
            "errors": null,
            "meta": {
                "total_battery_count":"1",
                "total_watt_capacity":"50",
                "avg_watt_capacity":"50"
            },
            "hasError": false
        }     
     ```


3. Fetch batteries where the battery capacity is bellow the given value

   - Endpoint URI: `/battery/watt-capacity/{max-capacity}/below`
   - Method : `GET`
   - Request Body Sample
       ```
       curl -X 'GET' 'http://localhost:8080/battery/watt-capacity/200/below' -H 'accept: application/json'
       ```
   - Response Body Sample
       ```
           {
              "data": [
                  {
                      "id": 1,
                      "name": "test",
                      "postCode": 2145,
                      "wattCapacity": 50,
                      "createdAt": "2023-08-26T03:14:10.834Z",
                      "modifiedAt": "2023-08-26T03:14:10.834Z"
                  }
              ],
              "errors": null,
              "meta": {
                  "total_battery_count":"1",
                  "total_watt_capacity":"50",
                  "avg_watt_capacity":"50"
              },
              "hasError": false
           } 
       ```
# Logging
- Synchronous logging with Log4j2 log configuration is added.
- Can consider adding Asynchronous logging to improve latency of the application by considering the facts like log message size, resource allocation, etc

# Monitoring
- Health Check - http://localhost:8080/actuator/health
- Correlation ID was added in log messages  for request tracing

# Testing
- Unit Testing : Included in the folder - `src/test`
- Integration Testing : Included in the folder - `src/integration-test`. 
  - tests are not Included. Test related to DB connection will be added here.
- Functional Testing : Included in the folder - `src/functional-test`.
  - tests are not Included. Testing of the full flow will cover with that by running acual server, without mocking

# Rate Limiting
- NOT IMPLEMENTED - Proposing to use Resilience4j rate limiter module (Advance library for rate limiting and circuit breakers)


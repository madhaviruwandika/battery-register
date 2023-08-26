# This was implemented as part of the coding challenge 

# Assumptions
1. Battery name and post code  is a unique value.
2. Once the batteries are registered battery information/updates are available via a stream (selected Kafka)

# High level Architecture

![My Image](highlevelArch.png)

### Battery-Registry-Service

See this page : [Documentation](battery-registry-service/README.md)

### Battery-Sync-Service

*** NOT IMPLEMENTED ***

This is a service which connects to a Kafka stream which have real time battery updates. This will be responsible for act as a processor which update database based on the messages. 

Having this as a separate service will be beneficial because it will not put load on battery-registry-service which has interface for querying data 

### Database

Given the concurrent environment of the application(battery registration and update with data streaming), Prefer to go with Relational database like PostgreSQL.
Test Application was developed with H2 in memory DB.

- DB Config
  - username and password are hardcoded since this is a testing application. Strictly avoiding these with live application.Environment variable will be used to pass values 
- Database Schema
  ```
    CREATE TABLE battery (
    id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    post_code VARCHAR(20) NOT NULL,
    watt_capacity INTEGER NOT NULL,
    created_at DATE NOT NULL,
    modified_at DATE,
    PRIMARY KEY (id),
    UNIQUE KEY unique_key_battery_name_post_code (name, post_code)
    );
  ```


- Indexes
    - Index on "post_code" : Proposing this as battery information is queries based on the post code 
    - Index on watt_capacity : Proposing this as battery information is queries based on the watt capacity




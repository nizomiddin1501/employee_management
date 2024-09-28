# Employee Management System

This project is an **Employee Management System** developed using **Spring Boot** and **PostgreSQL**. The system provides APIs for managing employees, salary calculations, and report generation. It also includes Swagger for API documentation.

## Table of Contents
- [Features](#features)
- [Technologies](#technologies)
- [Setup Instructions](#setup-instructions)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Calculation Types](#calculation-types)
- [Contact](#contact)

## Features

- **CRUD Operations**: Create, read, update, and delete employee data.
- **Salary Calculations**: Generate reports based on salary, pension, award, and vacation.
- **Reporting**: Retrieve reports about employee salary, region-based work, and more.
- **Swagger Integration**: Automatically generated API documentation.
- **Database Support**: PostgreSQL for persistent data storage.

## Technologies

- **Java 17**
- **Spring Boot** (Backend framework)
- **PostgreSQL** (Database)
- **Swagger** (API documentation)
- **Maven** (Build tool)
- **RESTful API** (For communication between client and server)

## Setup Instructions

### Prerequisites

Ensure you have the following installed:
- **Java 17**
- **PostgreSQL**
- **Maven**

### Database Setup

1. Create a PostgreSQL database:
   ```sql
   CREATE DATABASE employee_management;

2. Update your application.properties file with the correct database credentials:

spring.application.name=employee
spring.datasource.url=jdbc:postgresql://localhost:5432/employee_management
spring.datasource.username=postgres
spring.datasource.password=1234
spring.datasource.driver-class-name=org.postgresql.Driver
springdoc.swagger-ui.enabled=true
spring.jpa.hibernate.ddl-auto=update

### Running the Application

1. Clone the repository:

git clone <repository-url>
cd employee-management-system

2. Install the dependencies and build the project:

mvn clean install

3. Run the Spring Boot application:

mvn spring-boot:run

4. The application will be available at http://localhost:8080.

### API Documentation

The API documentation is available via Swagger. Once the application is running, navigate to the following URL to view the Swagger UI:

http://localhost:8080/swagger-ui/index.html


Swagger UI provides an interactive interface for testing the APIs. You can view all available endpoints, their descriptions, and test them directly from the browser.

### Calculation Types

The system supports the following calculation types for employees:

SALARY: Employee's monthly salary.
PENSION: Pension payments for retired employees.
AWARD: Awards or bonuses given to employees.
VACATION: Payments for employee vacations.



### Contact

For any inquiries or issues, feel free to contact:

Name: Nizomiddin Mirzanazarov
Email: nizomiddinmirzanazarov@gmail.com



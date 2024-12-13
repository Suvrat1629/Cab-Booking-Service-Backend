
# Cab Booking App Backend

This repository contains the backend implementation of a cab booking application similar to Ola, built using Spring Boot and Java. The backend provides REST APIs for managing users, drivers, cabs, and bookings, ensuring smooth functionality for a cab booking platform.

---

## Features

- **User Management**: Registration, login, and profile management.
- **Driver Management**: Registration, driver status updates, and location tracking.
- **Cab Booking**: Search for available cabs, book rides, and track booking history.

---

## Technology Stack

- **Backend Framework**: Spring Boot
- **Programming Language**: Java
- **Database**: MySQL
- **Build Tool**: Maven
- **Others**: Hibernate, Spring Data JPA, Spring Security

---

## Installation and Setup

### Prerequisites

1. Java Development Kit (JDK) 17 or higher
2. Maven 3.8+ installed
3. MySQL database server
4. Git installed on your machine

### Steps to Set Up

1. Clone the repository:
    ```bash
    git clone <your-repo-url>
    cd <repository-directory>
    ```

2. Configure the application properties:
    - Open `src/main/resources/application.properties`.
    - Update the database credentials:
      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/cab_booking
      spring.datasource.username=<your-db-username>
      spring.datasource.password=<your-db-password>
      ```

3. Build the project:
    ```bash
    mvn clean install
    ```

4. Run the application:
    ```bash
    mvn spring-boot:run
    ```

5. Access the application:
    - Swagger API Documentation: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## API Endpoints

### AuthController Endpoints
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - User login

### UserController Endpoints
- `GET /api/users/{id}` - Get user profile

### DriverController Endpoints
- `POST /api/drivers/register` - Register a new driver
- `PUT /api/drivers/{id}/status` - Update driver status
- `GET /api/drivers/available` - Get available drivers

### RideController Endpoints
- `POST /api/rides/create` - Create a new booking
- `GET /api/rides/{id}` - Get booking details
- `PUT /api/rides/{id}/cancel` - Cancel a booking

### HomeController Endpoints
- `GET /api/home` - Get app home information

---

## Database Schema

### Tables
- `users` - Stores user details
- `drivers` - Stores driver details
- `cabs` - Stores cab details
- `bookings` - Stores booking details

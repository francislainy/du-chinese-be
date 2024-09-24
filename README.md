# du-chinese-be

## Description
A backend service for managing users and lessons for the Du Chinese application.

## Technologies
- Java
- SQL
- Spring Boot
- Maven
- JUnit

## Overview
This project is a Spring Boot application that provides RESTful APIs for user management and lesson interactions. It includes functionalities such as creating users, favoriting lessons, and marking lessons as read or unread.

## Key Features
- User creation and management
- Favoriting lessons
- Marking lessons as read or unread
- Integration with PostgreSQL and H2 databases
- Unit and integration tests using JUnit and Mockito
- Pact for contract testing

## Longer Description
The `du-chinese-be` project is designed to handle backend operations for the Du Chinese application. It leverages Spring Boot for rapid development and includes various dependencies for web, security, and data management. The project also uses Lombok to reduce boilerplate code and includes comprehensive unit tests to ensure code quality.

## Technical Decisions
- **Spring Boot**: Chosen for its rapid development capabilities and extensive ecosystem.
- **PostgreSQL**: Used as the primary database for its robustness and scalability.
- **H2 Database**: Used for testing purposes to simplify setup and teardown.
- **JUnit and Mockito**: Used for unit testing to ensure code reliability.
- **Pact**: Used for contract testing to ensure API compatibility between services.

## Getting Started
### Prerequisites
- Java 17
- Maven

### Installation
1. Clone the repository:
    ```sh
    git clone https://github.com/francislainy/du-chinese-be.git
    ```
2. Navigate to the project directory:
    ```sh
    cd du-chinese-be
    ```
3. Build the project:
    ```sh
    mvn clean install
    ```

### Running the Application
To run the application, use the following command:
```sh
mvn spring-boot:run
```

### Running Tests
To run the tests, use the following command:

```sh
mvn test
```
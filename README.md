# BookStore API

This project is based on Maven and includes the following dependencies:

- [Spring Boot](https://spring.io/projects/spring-boot): The core framework for the project.
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa): Used for database access.
- [Spring Security](https://spring.io/projects/spring-security): Used for user authentication and authorization.
- [Liquibase](https://www.liquibase.org/): Used for database schema management.
- [PostgreSQL Driver](https://jdbc.postgresql.org/): Provides connectivity to the PostgreSQL database.
- [Lombok](https://projectlombok.org/): Simplifies Java classes.
- [Spring Boot Validation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-validation): Used for data validation.
- [Java JWT (JSON Web Tokens)](https://github.com/jwtk/jjwt): Used for JWT creation and validation.
- [Spring Boot Mail Starter](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-email.html): Used for sending emails.
- [SpringDoc OpenAPI Starter](https://springdoc.org/): Used for generating OpenAPI documentation.

These dependencies define the functionality and configuration of the project.


## Task Functional Requirements:

1. **User Registration and Login:**
   - Users can register as students or authors.
   - Secure authentication using JWT (JSON Web Tokens).

2. **Student Functionality:**
   - Students can log in and access books for reading.
   - View the list of books they're currently reading.
   - Subscribe to specific authors for email notifications.

3. **Author Functionality:**
   - Authors can log in and create new books.
   - Delete their own books.

4. **API for Retrieving Readers:**
   - Implement an API to retrieve a list of all readers for a specific book.

5. **Email Notifications:**
   - Students can subscribe to authors and receive email notifications with book details when a new book is published.

## Task Technical Details:
   - Employ PostgreSQL or MySql for data storage.
   - Define database tables using Liquibase.
   - Create an environment using Docker, ensuring that all components can be started with a single "docker-compose up" command.
   - Add unit tests if possible, the higher the test coverage, the better it is.

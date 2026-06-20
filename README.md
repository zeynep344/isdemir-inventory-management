# ISDEMIR Inventory Management

This is a Spring Boot project developed for managing product records in an inventory system. The project includes separate modules for different product types such as hot coil, cold coil, and plate.

The main purpose of the project is to practice basic backend development with Spring Boot, database operations, service-repository structure, and simple web page routing with Thymeleaf.

## Technologies

* Java
* Spring Boot
* Spring Data JPA
* Spring Security
* Thymeleaf
* Maven
* HTML / CSS
* MySQL

## Project Structure

```text
src/main/java/com/example/isdemir
├── config
├── model
├── repository
├── service
├── user
├── AuthController.java
├── CommonController.java
├── DashboardController.java
├── ProductController.java
├── TypeSelectController.java
└── IsdemirApplication.java
```

```text
src/main/resources
├── static
├── templates
└── application.properties
```

## Main Features

* User login and registration
* Password encryption
* Role/permission based user structure
* Product management
* Hot coil management
* Cold coil management
* Plate management
* Web pages created with Thymeleaf templates
* Database operations with Spring Data JPA repositories

## Product Types

The project separates product-related operations into different model, repository, and service classes.

Current product types:

* Product
* HotCoil
* ColdCoil
* Plate

## Running the Project

Clone the repository:

```bash
git clone https://github.com/zeynep344/isdemir-inventory-management.git
```

Go to the project folder:

```bash
cd isdemir-inventory-management
```

Run the project with Maven Wrapper:

```bash
./mvnw spring-boot:run
```

For Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

The project runs on `http://localhost:8081`.

Login page: `http://localhost:8081/login-page`.

## Database Configuration

Database connection settings are located in:

```text
src/main/resources/application.properties
```

Before running the project, make sure that the database connection information is correct.


## Notes

The project was mainly built to practice Spring Boot, database operations, service-repository structure, and basic page routing with Thymeleaf. 

## Author

Zeynep

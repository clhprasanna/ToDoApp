# Spring Boot Todo Application

This repository contains a Spring Boot application that provides multiple RESTful API's to manage a todo list. The application uses Spring Boot with an SQLite database for storage and is containerized using Docker.

## Features

- CREATE, RETRIEVE, UPDATE, DELETE operations for todo items
- Docker integration for easy deployment and testing
- OpenAPI Spec for API documentation

## API Endpoints
The application defines the following RESTful endpoints:

- GET /api/todos: List all todos
- POST /api/todos: Create a new todo
- GET /api/todos/{id}: Retrieve a todo by ID
- PATCH /api/todos/{id}: Update a todo
- DELETE /api/todos/{id}: Delete a todo

## Requirements

- Java JDK 17
- Gradle (for building and running the project)
- Docker (optional, for containerization)


## Building and Running the application

Below instructions will help you to run the application on the local machine for development and testing purposes. 

### Building the application

To build the application, run the following command from the root of the project:

`./gradlew clean build`

### Running the Application

To run the application, run the following command from the root of the project:

`./gradlew bootRun`

### Running with Docker

To build the Docker image and run the application using Docker, execute:

_`docker build -t todo-app .`_

`docker run -p 8080:8080 todo-app`

### API Documentation

The API documentation in Open API specification can be found in the following location,

_spec/todo-api-spec.yaml_ file_

### Postman Collection

The Postman collection for testing the API's in this project can be found in the following location,

_postman-collection/TodoApp-APIClient.postman_collection.json_


## Recommended Improvements

- Support for application logging needs to be added/configured using SLF4J
- Java Docs can be provided for each of the classes and public methods
- Custom exceptions can be thrown (and handled) in case of specific error scenarios
- Code coverage check can be configured using the Jacoco Open source library

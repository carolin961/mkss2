
Natürlich! Hier ist die komplette README.md im Markdown-Format:

markdown
Code kopieren
# Robot Service

The Robot Service is a RESTful web service for managing robots and orchestrating their actions. It provides endpoints to add new robots, perform tasks, and retrieve action histories.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- **Java Development Kit (JDK)**: Version 17 or higher.
- **Maven**: Version 3.9.8 or higher.
- **Docker**: Version 20.10 or higher (for running the application in a container).

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/carolin961/mkss2.git
cd mkss2
```

### Build the Project
Use Maven to build the project:
```bash
mvn clean install
```

### Running Tests
To only run the tests:
```bash
mvn test
```

## Run the Application
You can run the application using Maven:
```bash
mvn spring-boot:run
```

## Access the Application
Once the application is running, you can access it at:

http://localhost:8080

Alternatively you can access the application via swagger on:

http://localhost:8080/swagger-ui/index.html#/


## Run the Application with Docker

### Build the Docker Image:
```bash
docker build -t robot-service .
```

### Run the Docker Container:
```bash
docker run -p 8080:8080 robot-service
```

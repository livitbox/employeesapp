# Employees Service

A Spring Boot REST API service for managing employees with PostgreSQL database and Liquibase migrations.

## Prerequisites

Before running this application, ensure you have the following installed:

- **Java 21**
- **Maven 3.8+**
- **Docker Compose** - Usually included with Docker Desktop

## Database Setup

### Starting PostgreSQL with Docker Compose

1. Navigate to the docker-compose directory:
   ```bash
   cd docker-compose
   ```

2. Start the PostgreSQL database and Adminer:
   ```bash
   docker-compose up -d
   ```

3. Verify the containers are running:
   ```bash
   docker-compose ps
   ```

### Accessing Adminer (Database UI)

1. Open your browser and go to `http://localhost:9000`

### Stopping the Database

To stop the database containers:
```bash
cd docker-compose
docker-compose down
```

To stop and remove all data:
```bash
docker-compose down -v
```

## Application Setup

### Install Dependencies

Using Maven wrapper (recommended):
```bash
./mvnw clean install
```

Or using Maven:
```bash
mvn clean install
```

### 4. Start the Application

Using Maven wrapper:
```bash
./mvnw spring-boot:run
```

Or using Maven:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Database Migrations

Liquibase will automatically run database migrations on application startup. The migrations are located in:
```
src/main/resources/db/changelog/
```

## API Documentation

Once the application is running, you can access the API documentation:

- **Swagger UI**: `http://localhost:8080/api/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/api/api-docs`

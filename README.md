# NexusBank Microservices

NexusBank is a modern banking application built with a microservices architecture. The project includes both backend microservices and a React frontend for a complete banking system solution.

## Table of Contents
- [Overview](#overview)
- [Project Architecture](#project-architecture)
- [Microservices](#microservices)
- [Frontend](#frontend)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)

## Overview

NexusBank modernizes banking infrastructure with a microservices-based backend and a React frontend. The system manages customers and their banking accounts (current and savings) through REST APIs.

## Project Architecture

The application is built using a microservices architecture with the following components:

```
┌─────────────────┐     ┌─────────────────┐
│    React UI     │────▶│  Gateway Service │
└─────────────────┘     └────────┬────────┘
                                 │
                                 ▼
┌─────────────────┐     ┌─────────────────┐
│  Config Service │◀────│Discovery Service │
└─────────────────┘     └────────┬────────┘
                                 │
                      ┌──────────┴──────────┐
                      │                     │
               ┌──────▼─────┐        ┌──────▼─────┐
               │  Customer  │◀───────│   Account  │
               │  Service   │        │   Service  │
               └──────┬─────┘        └──────┬─────┘
                      │                     │
                      ▼                     ▼
               ┌──────────────┐      ┌──────────────┐
               │   Customer   │      │    Account   │
               │   Database   │      │   Database   │
               └──────────────┘      └──────────────┘
```

## Microservices

1. **Config Service** (Port 8888): Central configuration management
   - Manages configuration for all services
   - Uses Spring Cloud Config with Git backend

2. **Discovery Service** (Port 8761): Service registry
   - Implements Netflix Eureka for service discovery
   - Provides service registration and discovery

3. **Gateway Service** (Port 8080): API Gateway
   - Single entry point for all API requests
   - Routes requests to appropriate microservices
   - Implements Spring Cloud Gateway

4. **Customer Service** (Port 8081): Customer management
   - Manages customer data
   - Provides customer creation, retrieval, and listing functionality
   - Connected to PostgreSQL database

5. **Account Service** (Port 8082): Account management
   - Manages account data including savings and current accounts
   - Provides account creation, retrieval, and listing functionality
   - Connected to PostgreSQL database

## Frontend

The application includes a React frontend that consumes the APIs provided by the microservices:

- **Material UI**: Modern interface for banking operations
  - Customer registration and management
  - Account creation and management
  - Responsive design

## Technologies

### Backend
- **Spring Boot**: Core framework
- **Spring Cloud**: Microservices ecosystem
  - Spring Cloud Config
  - Spring Cloud Gateway
  - Netflix Eureka
- **Spring Data JPA**: Database access
- **PostgreSQL**: Relational database
- **Maven**: Dependency management
- **Docker**: Containerization

### Frontend
- **React**: Frontend library
- **Axios**: HTTP client
- **React Router**: Navigation
- **Tailwind/Material UI**: UI components

## Getting Started

### Prerequisites
- Java 8
- Maven
- Docker & Docker Compose
- Node.js and npm (for the frontend)

### Running the Application

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Benfill/nexusbank-microservices.git
   cd nexusbank-microservices
   ```

2. **Build services**:
   ```bash
   mvn clean package -f config-service/pom.xml
   mvn clean package -f discovery-service/pom.xml
   mvn clean package -f gateway-service/pom.xml
   mvn clean package -f customer-service/pom.xml
   mvn clean package -f account-service/pom.xml
   ```

3. **Run with Docker Compose**:
   ```bash
   docker-compose up --build
   ```

4. **Start the frontend** (in a separate terminal):
   ```bash
   cd frontend
   npm install
   npm start
   ```

5. **Access the application**:
   - Frontend: http://localhost:3000
   - Eureka Dashboard: http://localhost:8761
   - API Gateway: http://localhost:8080

## API Documentation

### Customer Service APIs

- **GET /api/customers**: Get all customers
- **GET /api/customers/{id}**: Get customer by ID
- **POST /api/customers**: Create a new customer
- **DELETE /api/customers/{id}**: Delete a customer

### Account Service APIs

- **GET /api/accounts**: Get all accounts
- **GET /api/accounts/{id}**: Get account by ID
- **GET /api/accounts/customer/{customerId}**: Get accounts by customer ID
- **POST /api/accounts**: Create a new account

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

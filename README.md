# 🚀 E-Commerce Backend (Spring Boot 3.5.11)

**Production-ready e-commerce REST API** with pagination, security, and full database support.

## ✨ Features

- ✅ **Products API** - Pagination, filtering, sorting
- ✅ **Categories API** - Hierarchical categories  
- ✅ **Users API** - User management
- ✅ **Orders API** - Order processing
- ✅ **Payments API** - Transaction management
- ✅ **Swagger UI** - Interactive API documentation
- ✅ **Flyway Migrations** - Database versioning
- ✅ **HikariCP** - Connection pooling
- ✅ **Spring Security** - JWT ready
- ✅ **JPA/Hibernate** - Optimized queries

## 🛠️ Tech Stack
Backend: Spring Boot 3.5.11 | Spring Data JPA | PostgreSQL
API Docs: SpringDoc OpenAPI 3 (Swagger)
Database: Flyway | HikariCP
Security: Spring Security 6.5.8
Validation: Bean Validation 3.1
Testing: JUnit 5 | Spring Boot Test


## 🚀 Quick Start

### 1. Clone & Install
```bash
git clone <your-repo>
cd week7-ecommerce-backend
mvn clean install
```
### 2. Start PostgreSQL
```bash
docker run -d \
  --name postgres-ecommerce \
  -e POSTGRES_DB=ecommerce_db \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=password \
  -p 5432:5432 \
  postgres:16
```
### 3. Configure Database
application.yml:
```bash
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/ecommerce_db
    username: postgres
    password: password
```
### 4. Run Application
```bash
mvn spring-boot:run
```
### 5. API Documentation
```bash
Swagger UI: http://localhost:8080/swagger-ui.html
API Docs:  http://localhost:8080/v3/api-docs
```
## 📋 API Endpoints

| Method | Endpoint           | Description                |
| ------ | ------------------ | -------------------------- |
| GET    | /api/products      | List products (pagination) |
| GET    | /api/products/{id} | Get single product         |
| GET    | /api/categories    | List categories            |
| GET    | /api/users         | List users (admin)         |
| GET    | /api/orders        | List orders                |
| GET    | /api/payments      | List payments              |

## 🧪 Testing
```bash
# Unit Tests
mvn test

# Integration Tests  
mvn test -Dtest=*IntegrationTest

# API Tests (Postman/Swagger)
curl "http://localhost:8080/api/products?page=0&size=5"
```
## 🗄️ Database Schema
```bash
users → orders → order_items → products
     ↘ payments
products → categories (hierarchical)
```
### Flyway Migrations: src/main/resources/db/migration/

## 🔧 Configuration
| Property              | Default | Description           |
| --------------------- | ------- | --------------------- |
| server.port           | 8080    | Application port      |
| spring.datasource.url | -       | PostgreSQL connection |
| spring.jpa.show-sql   | false   | Show SQL queries      |
| spring.flyway.enabled | true    | Database migrations   |

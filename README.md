
# Product Service - Spring Boot 3.x + Redis Cache

A complete Spring Boot REST API for managing Products with **MySQL** as primary database and **Redis** as cache (Cache-Aside Pattern).

## Technologies Used

- **Spring Boot 3.3+**
- **Java 21**
- **MySQL 8**
- **Redis 7**
- **Spring Data JPA**
- **Spring Cache + Redis**
- **Validation**
- **Swagger / OpenAPI**
- **Docker & Docker Compose**

---

## Features

- CRUD operations for Products
- Request validation using Jakarta Validation
- Global Exception Handling
- Cache-Aside Pattern with Redis (GET, PUT, DELETE)
- Swagger UI Documentation
- Fully containerized with Docker Compose

---

## How to Run the Application

### 1. Using Docker Compose (Recommended)

```bash
# Clone the project
git clone <your-repo-url>
cd product-service

# Build and start all containers
docker-compose up --build -d


2. Check if all services are running

docker ps

You should see three containers:

mysql
redis
app

API Endpoints

Method,Endpoint,Description,Request Body
POST,/api/products,Create new product,ProductRequest
GET,/api/products/{id},Get product by ID (Cached),-
GET,/api/products,Get all products,-
PUT,/api/products/{id},Update product (Cache Updated),ProductRequest
DELETE,/api/products/{id},Delete product (Cache Evicted)

Swagger UI
Open in browser:
http://localhost:8080/swagger-ui.html

Accessing Databases
MySQL

docker exec -it mysql mysql -u root -p
# Password: rootpassword

USE productdb;
SELECT * FROM products;

Redis

docker exec -it redis redis-cli

KEYS *
GET products::1

Project Structure

src/main/java/com/example/productservice/
├── config/
│   ├── CacheConfig.java
│   ├── JacksonConfig.java
├── controller/
│   └── ProductController.java
├── dto/
│   ├── ProductRequest.java
│   └── ProductResponse.java
├── entity/
│   └── Product.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   └── ProductNotFoundException.java
├── repository/
│   └── ProductRepository.java
├── service/
│   └── ProductService.java
└── ProductServiceApplication.java


Docker Compose Commands

# Start all services
docker-compose up --build -d

# View logs
docker-compose logs -f product-service

# Stop all services
docker-compose down

# Stop and remove volumes (clear database)
docker-compose down -v

# Rebuild only application
docker-compose up --build product-service


Environment Variables (application.yml)

MySQL: jdbc:mysql://mysql:3306/productdb
Redis: redis:6379


Testing the Flow

1. Create a product → POST /api/products
2. Get the product → GET /api/products/{id} (First time → DB, Second time → Redis Cache)
3. Check Redis cache using KEYS *
4. Update/Delete and verify cache behavior

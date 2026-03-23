# RenjiCart

RenjiCart is a backend e-commerce API built with **Java**, **Spring Boot**, **Spring Data JPA**, and **MySQL**.  
The project currently focuses on the core backend functionality needed for an online store, including product browsing, filtering, reviews, and order creation.

---

## Features

### 1) Product Catalog

- Fetch all products with pagination
- Fetch a single product by ID
- Search products using dynamic filters
- Return product details including pricing, seller, stock, ratings, images, and reviews

### 2) Product Search & Filtering

Filtering is implemented using **Spring Data JPA Specifications**, which allows flexible query composition.

Supported filters:

- `category`
- `minPrice`
- `maxPrice`
- `keyword` (name/description)
- `ratings`

### 3) Product Reviews

- Add a review for an existing product
- Persist product reviews in the database
- Associate reviews with products

### 4) Order Management

- Create an order from a list of order items
- Validate ordered product IDs against existing products
- Calculate item total
- Add tax amount
- Generate a unique order reference ID
- Retrieve an order by reference ID

---

## Tech Stack

- **Java 21**
- **Spring Boot 3.5.x**
- **Spring Web**
- **Spring Data JPA**
- **Spring Validation**
- **Spring Security** _(dependency present, currently not active in runtime flow)_
- **MySQL**
- **Maven**

---

## Project Structure

```text
renjicart/
├── src/
│   ├── main/
│   │   ├── java/com/prabthejas/renjicart/
│   │   │   ├── controller/
│   │   │   │   ├── OrderController.java
│   │   │   │   ├── ProductController.java
│   │   │   │   └── ProductReviewController.java
│   │   │   ├── dto/
│   │   │   ├── entity/
│   │   │   ├── repository/
│   │   │   ├── seed/
│   │   │   ├── service/
│   │   │   ├── spec/
│   │   │   └── RenjicartApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/prabthejas/renjicart/
└── pom.xml
```

---

## Architecture Overview

RenjiCart currently follows a conventional Spring Boot layered design:

- **Controller layer**: exposes REST endpoints
- **Service layer**: contains application/business logic
- **Repository layer**: handles persistence through Spring Data JPA
- **Entity layer**: defines domain models mapped to MySQL tables
- **DTO layer**: shapes API request/response payloads
- **Specification layer**: handles dynamic product search filtering
- **Seed layer**: populates initial demo products for local development

This structure is a good base for scaling the codebase toward more production-oriented modules such as authentication, carts, payments, admin flows, and inventory control.

---

## Domain Model (Current)

### Product

Represents a catalog item with fields such as:

- name
- price
- description
- category
- ratings
- seller
- stock
- review count
- images
- reviews

### ProductImage

Represents image metadata associated with a product.

### ProductReview

Represents a review for a product, including comment and rating.

### Order

Represents a placed order with:

- status
- order items
- item total
- tax amount
- total amount
- generated reference ID

### OrderItem

Represents an item inside an order and links back to a product.

---

## API Endpoints

## Base URL

```http
http://localhost:8080
```

### Product APIs

#### Get paginated products

```http
GET /api/products?page=0&size=5
```

**Example response shape**

```json
{
  "products": [
    {
      "id": 1,
      "name": "Sample Product",
      "price": 799.0,
      "description": "Product description",
      "category": "Electronics",
      "ratings": 4.5,
      "seller": "RenjiCart",
      "stock": 10,
      "numOfReviews": 2,
      "images": [],
      "reviews": []
    }
  ],
  "totalProducts": 6
}
```

#### Get product by ID

```http
GET /api/products/{id}
```

#### Search products

```http
GET /api/products/search?category=Electronic&minPrice=100&maxPrice=1000&keyword=iphone&ratings=4.0
```

**Supported query params**

- `category`
- `minPrice`
- `maxPrice`
- `keyword`
- `ratings`

---

### Review APIs

#### Add a product review

```http
POST /api/products/reviews
Content-Type: application/json
```

**Sample request**

```json
{
  "productId": 1,
  "comment": "Great product for the price.",
  "rating": 4.5
}
```

**Sample response**

```text
Review Added
```

---

### Order APIs

#### Create an order

```http
POST /api/orders
Content-Type: application/json
```

**Sample request**

```json
{
  "orderItems": [
    {
      "name": "Apple iPhone 15",
      "quantity": 1,
      "image": "/products/1.jpg",
      "price": 799.0,
      "productId": 1
    }
  ]
}
```

**Sample response**

```json
{
  "referenceId": "e7b5f2af-4f44-4a84-9e61-5b2b9d3d40ad"
}
```

#### Get order by reference ID

```http
GET /api/orders/{referenceId}
```

---

## Local Setup

### Prerequisites

Make sure you have the following installed:

- Java 21
- Maven
- MySQL 8+

### 1) Clone the repository

```bash
git clone https://github.com/thejasprab/RenjiCart.git
cd RenjiCart/renjicart
```

### 2) Create the database

```sql
CREATE DATABASE renjicart;
```

### 3) Configure application properties

Update `src/main/resources/application.properties` with your local MySQL credentials.

Example configuration:

```properties
spring.application.name=renjicart
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/renjicart
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql=true
spring.web.resources.static-locations=file:uploads/
```

### 4) Run the application

Using Maven wrapper:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

Or using Maven directly:

```bash
mvn spring-boot:run
```

The API will typically start on:

```text
http://localhost:8080
```

---

## Future Features / Next-Level Improvements

This section is intended to be maintained as the project evolves.

### Near-Term Improvements

- Add global exception handling with clear API error responses
- Add request validation annotations and validation-friendly response bodies
- Improve DTO design and response consistency
- Add Swagger / OpenAPI documentation
- Add proper logging strategy
- Add environment-specific configuration
- Add test coverage for controllers and services

### Commerce Features

- Persistent shopping cart
- User registration and login
- Role-based access control for admin/customer flows
- Admin APIs for product CRUD
- Inventory deduction and stock validation on order placement
- Address and shipping details
- Coupon/discount support
- Order history per user
- Order status transitions

### Production-Oriented Enhancements

- JWT-based authentication and authorization
- Payment gateway integration
- Dockerized local development and deployment
- CI/CD pipeline
- Centralized configuration and secrets handling
- Better database migration strategy (Flyway or Liquibase)
- Monitoring and health checks
- Caching for catalog reads
- API versioning

### Possible Frontend / Full-Stack Expansion

- React-based storefront
- Product detail page and catalog UI
- Cart and checkout flow
- User account dashboard
- Admin dashboard

---

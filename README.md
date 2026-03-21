# RenjiCart

RenjiCart is a backend e-commerce API built with **Java**, **Spring Boot**, **Spring Data JPA**, and **MySQL**.  
The project currently focuses on the core backend functionality needed for an online store, including product browsing, filtering, reviews, and order creation.

This repository represents the backend foundation of an e-commerce system and is being developed incrementally.

---

## Features Implemented

- Browse all products with pagination
- Fetch product details by ID
- Search products using dynamic filters:
  - category
  - minimum price
  - maximum price
  - keyword in name/description
  - minimum rating
- Add product reviews
- Create orders from selected products
- Retrieve orders using a generated reference ID
- Automatically seed demo products on first run
- Store product image references for static serving

---

## Tech Stack

- **Java 21**
- **Spring Boot 3**
- **Spring Web**
- **Spring Data JPA**
- **Spring Validation**
- **MySQL**
- **Maven**

---

## Project Structure

```text
src/main/java/com/prabthejas/renjicart
├── controller
│   ├── OrderController.java
│   ├── ProductController.java
│   └── ProductReviewController.java
├── dto
│   ├── OrderCreated.java
│   ├── OrderDto.java
│   ├── OrderItemDto.java
│   ├── ProductDto.java
│   ├── ProductImageDto.java
│   └── ProductReviewDto.java
├── entity
│   ├── Order.java
│   ├── OrderItem.java
│   ├── Product.java
│   ├── ProductImage.java
│   └── ProductReview.java
├── repository
│   ├── OrderRepository.java
│   ├── ProductRepository.java
│   └── ProductReviewRepository.java
├── seed
│   └── ProductSeeder.java
├── service
│   ├── OrderService.java
│   └── ProductService.java
├── spec
│   └── ProductSpecification.java
└── RenjicartApplication.java
```

---

## How It Works

### Product Module

The product module allows users to:

- list all products with pagination
- retrieve a single product by ID
- search products using dynamic query parameters

Filtering is implemented using **JPA Specifications**, which makes the search flexible and scalable.

### Review Module

Users can add reviews to a product through a dedicated API endpoint.  
Each review is linked to a product and stored in the database.

### Order Module

Orders are created using a list of order items.  
When an order is placed:

- products are validated using their IDs
- total item amount is calculated
- a fixed tax amount is added
- a unique order reference ID is generated
- the order is persisted in the database

---

## API Endpoints

### Products

#### Get all products

```http
GET /api/products?page=0&size=5
```

#### Get product by ID

```http
GET /api/products/{id}
```

#### Search products

```http
GET /api/products/search?category=Electronic&minPrice=100&maxPrice=1000&keyword=iphone&ratings=4.5
```

---

### Product Reviews

#### Add a review

```http
POST /api/products/reviews
Content-Type: application/json
```

Sample request body:

```json
{
  "productId": 1,
  "comment": "Great product",
  "rating": 4.5
}
```

---

### Orders

#### Create an order

```http
POST /api/orders
Content-Type: application/json
```

Sample request body:

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

Sample response:

```json
{
  "referenceId": "generated-uuid-value"
}
```

#### Get order by reference ID

```http
GET /api/orders/{referenceId}
```

---

## Database Configuration

The application is configured to use MySQL.

Current configuration in `application.properties`:

```properties
spring.application.name=renjicart
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/renjicart
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql=true
spring.web.resources.static-locations=file:uploads/
```

Make sure you create a MySQL database named:

```sql
CREATE DATABASE renjicart;
```

You may also need to update the username and password based on your local MySQL setup.

---

## Running the Project Locally

### Prerequisites

- Java 21
- Maven
- MySQL

### Steps

1. Clone the repository:

```bash
git clone https://github.com/thejasprab/RenjiCart.git
cd RenjiCart/renjicart
```

2. Configure MySQL in `src/main/resources/application.properties`

3. Create the database:

```sql
CREATE DATABASE renjicart;
```

4. Run the application:

```bash
./mvnw spring-boot:run
```

Or on Windows:

```bash
mvnw.cmd spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

---

## Demo Data

On startup, the application seeds demo products if the products table is empty.

The seeded catalog currently includes sample electronics such as:

- iPhone
- Samsung Galaxy
- Pixel
- MacBook
- Dell XPS
- Sony headphones
- smartwatches
- accessories

This makes it easier to test product listing, search, and order creation without manually inserting records.

---

## Current Status

This project is currently in progress and represents the **backend foundation** of a larger e-commerce platform.

### Implemented

- product APIs
- search/filter logic
- review creation
- order creation and retrieval
- MySQL persistence
- demo seeding

### Not Yet Implemented / Future Work

- authentication and authorization
- cart management
- user accounts
- admin product management
- update/delete product endpoints
- payment integration
- inventory checks during order placement
- exception handling with proper error responses
- Swagger / API documentation
- Docker deployment
- frontend integration

---

## Notes

- `spring-security` is included as a dependency, but default security is currently disabled in the application:
  ```java
  @SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
  ```
- The current order tax amount is fixed in code.
- Review submission currently stores reviews, but product rating aggregation and review count updates can be improved further.
- This project is designed as a learning and portfolio project to demonstrate backend engineering concepts using Spring Boot.

---

## Learning Goals Behind This Project

This project was built to practice and demonstrate:

- REST API design
- layered backend architecture
- DTO usage
- entity relationships in JPA
- pagination and filtering
- order flow modeling
- database integration with MySQL

---

## Author

**Thejas Prabakaran**

If you found this project interesting, feel free to explore the codebase and follow its progress.

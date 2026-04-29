# 🐐 GenzStore Backend

A modern, scalable backend service for **GenzStore**, built with **Spring Boot**. Designed with clean architecture, performance, and developer experience in mind.

---

## 📌 Overview

GenzStore Backend powers the core functionality of the platform — handling users, products, orders, authentication, and more. It follows best practices in REST API design, security, and modular development.

---

## 🛠️ Tech Stack

* **Java 17+**
* **Spring Boot**
* **Spring Security**
* **Spring Data JPA (Hibernate)**
* **MongoDB**
* **Maven / Gradle**
* **JWT Authentication**
* **Lombok**
* **Docker (optional)**

---

## 📂 Project Structure

```
genzstore-backend/
src
├── main
│   ├── java
│   │   └── com
│   │       └── cg
│   │           └── genzstore
│   │               ├── config
│   │               │   ├── SecurityConfig.java
│   │               │   └── WebConfig.java
│   │               ├── controllers
│   │               │   ├── AuthController.java
│   │               │   ├── CartController.java
│   │               │   ├── ProductController.java
│   │               │   └── UserController.java
│   │               ├── GenZStoreApplication.java
│   │               ├── model
│   │               │   ├── dto
│   │               │   │   ├── ApiResponse.java
│   │               │   │   ├── ProductDTO.java
│   │               │   │   ├── ProductRequestDTO.java
│   │               │   │   ├── UserCreateDTO.java
│   │               │   │   ├── UserDTO.java
│   │               │   │   └── UserRequestDTO.java
│   │               │   └── entity
│   │               │       ├── CartItem.java
│   │               │       ├── Cart.java
│   │               │       ├── OrderItem.java
│   │               │       ├── Order.java
│   │               │       ├── Product.java
│   │               │       └── User.java
│   │               ├── repository
│   │               │   ├── CartRepository.java
│   │               │   ├── OrderRepository.java
│   │               │   ├── ProductRepository.java
│   │               │   └── UserRepository.java
│   │               └── service
│   │                   ├── CartService.java
│   │                   ├── JwtService.java
│   │                   ├── ProductService.java
│   │                   ├── TokenAllowlistService.java
│   │                   └── UserService.java
│   └── resources
│       └── application.yaml
│
└── pom.xml
```

---

## 🔐 Features

* ✅ User Authentication & Authorization (JWT-based)
* 🛍️ Product Management
* 📦 Order Processing
* 👤 User Profiles
* 🧾 Role-Based Access Control
* ⚡ Optimized REST APIs
* 📊 Pagination & Filtering
* 🧩 Modular & Scalable Architecture

---

## ⚙️ Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/genzstore-backend.git
cd genzstore-backend
```

### 2. Configure Database

Update your `application.yml`:

```yaml
spring:
  datasource:
    uri: mongodb://

```

### 3. Build & Run

```bash
mvn clean install
mvn spring-boot:run
```


---

## 🔑 API Authentication

* Uses **JWT (JSON Web Tokens)**
* Include token in headers:

```http
Authorization: Bearer <your_token>
```

---

## 📡 API Endpoints (Sample)

| Method | Endpoint           | Description       |
| ------ | ------------------ | ----------------- |
| POST   | /api/auth/login    | User login        |
| POST   | /api/auth/register | Register new user |
| GET    | /api/products      | Get all products  |
| POST   | /api/orders        | Create new order  |

---

## 🧪 Testing

Run tests with:

```bash
mvn test
```

---

## 📈 Future Enhancements

* 🔍 Advanced Search & Filtering
* 💳 Payment Gateway Integration
* 📊 Analytics Dashboard
* 🌐 Microservices Architecture

---

---

## 💡 Author

Built with ❤️ for the next-gen eCommerce experience.

---

> “Code smart. Scale fast. Stay GenZ.” 😎

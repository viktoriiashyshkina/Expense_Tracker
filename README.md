# 💸 Expense Tracker API

This is a full-stack application built with a **Spring Boot** backend and a **React** frontend.  
It helps users manage their expenses, categorize them, and track balances over time.

---

## 📦 Features

- User registration & login (JWT authentication)
- Secure REST API with Spring Security
- Add, update, delete transactions
- Organize transactions into categories
- Swagger UI for API testing
- PostgreSQL database
- **Frontend built with React**

---

## 🧩 Technologies Used

**Backend:**

- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Security (JWT)
- PostgreSQL
- Springdoc OpenAPI (Swagger)
- Lombok
- Maven

**Frontend:**

- React
- React Router
- Axios (for API requests)
- CSS Modules / Styled Components

---

## 📑 API Overview

Base URL: `http://localhost:8080`

### 🔐 Authentication

| Method | Endpoint         | Description      |
|--------|------------------|------------------|
| POST   | `/api/signup`    | Register user    |
| POST   | `/api/login`     | Login & get JWT  |
| GET    | `/api/home`      | Public home page |

---

### 💼 Transactions

| Method | Endpoint                             | Description                |
|--------|--------------------------------------|----------------------------|
| GET    | `/api/transactions`                  | List all transactions      |
| POST   | `/api/transactions/addTransaction`   | Add a new transaction      |
| POST   | `/api/transactions/updateTransaction`| Update an existing one     |
| DELETE | `/api/transactions/delete/{id}`      | Delete a transaction       |

---

### 🗂️ Categories

| Method | Endpoint          | Description         |
|--------|-------------------|---------------------|
| POST   | `/api/categories` | Create a category   |

---

### 👤 User Dashboard

| Method | Endpoint     | Description       |
|--------|--------------|-------------------|
| GET    | `/dashboard` | Get user info     |

---

## 🧪 Swagger Docs

View interactive API documentation at:  
📎 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  
📎 Raw JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## ⚙️ Sample Configuration

### `application.properties`
```properties
spring.application.name=task-tracker
spring.datasource.url=jdbc:postgresql://localhost:5432/tracker
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=create-drop

springdoc.api-docs.enabled=true
springdoc.swagger-ui.enabled=true
````
🛠 How to Run
Backend
Clone the repository

Set up PostgreSQL database tracker

Update DB credentials in application.properties

Run the backend:

bash
Kopieren
Bearbeiten
./mvnw spring-boot:run
Frontend
Navigate to the frontend directory

Install dependencies:

```bash
npm install
````
Start the React app:

```bash
npm start

````


🧑‍💻 Author
Viktoriia Shyshkina

# Secure Social Media API

A production-grade REST API for a social media platform, built with **Spring Boot** and **Spring Security**. This project demonstrates enterprise-level authentication, security best practices, and RESTful API design suitable for financial and security-critical applications.

## 🔐 Key Features

### Security & Authentication
- **JWT Token-Based Authentication** with access and refresh token implementation
- **Spring Security** integration with stateless session management
- **BCrypt Password Hashing** with proper salt rounds for secure credential storage
- **Refresh Token Rotation** for enhanced security and token revocation capability
- **Token Revocation** through database-backed refresh token management
- **Input Validation** and comprehensive error handling
- **OWASP Security Best Practices** including protection against common vulnerabilities

### Core Functionality
- User registration and authentication with security validations
- CRUD operations for user-generated content (messages)
- RESTful API design with proper HTTP status codes and error responses
- Database persistence with Spring Data JPA
- Relationship management between users and their content

## 🛠️ Technologies Used

**Backend Framework:**
- Java 8+
- Spring Boot 2.7+
- Spring Security
- Spring Data JPA
- Spring MVC

**Security:**
- JWT (JSON Web Tokens) - JJWT library
- BCrypt Password Encoder
- Stateless Authentication

**Database:**
- JPA/Hibernate ORM
- MySQL

**Build Tool:**
- Maven

## 📋 Prerequisites

- Java 8 or higher
- Maven 3.6+
- VS Code

## 🚀 Getting Started

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/dev-atobatele/social-media-api.git
cd social-media-api

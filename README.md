# E-Commerce Grocery Platform

This project is a fully functional eCommerce grocery platform built using a microservices architecture, designed to provide a seamless user experience and efficient management of various business functionalities.

## Technology Stack

- **Backend:** Java Spring Boot
- **Frontend:** React, Tailwind CSS
- **Database:** MySQL

## Microservices Overview

The platform is developed using a collection of independent microservices, each responsible for a specific domain of functionality:

1. **User Service**
   - Manages user functionalities: registration, login, logout, profile management, and dynamic user detail updates.
   - Implements JWT-based authentication and authorization for secure access.
   - Includes "Forgot Password" functionality using SMTP for secure password reset tokens.

2. **Product Service**
   - Manages the product catalog, including product listings and detailed information.
   - Supports filtering and sorting based on ratings and other custom criteria.

3. **Review Service**
   - Handles the management of product reviews.
   - Ensures reviews are properly associated with specific products.

4. **Cart Service**
   - Manages shopping cart functionalities: adding, updating, and removing products from the cart.
   - Provides a dynamic and responsive interface for managing cart contents.

5. **Order Service**
   - Facilitates the complete order lifecycle from creation to purchase.
   - Manages order reviews and the selection of default delivery addresses.

## Notable Exclusions

- **Payment Service:**
  - The current implementation does not include a Payment Service. This service is planned for future integration to handle payment processing.

## Key Features

- **Microservices Architecture:**
  - Ensures scalability, modularity, and ease of maintenance by allowing each service to operate independently.

- **JWT Authentication and Authorization:**
  - Provides secure access and protects user data with JSON Web Tokens (JWT).

- **SMTP Integration for Password Reset:**
  - Enables secure password recovery via email.

- **Advanced Product Management:**
  - Offers product cataloging, listing, sorting, and filtering capabilities.

- **User-Centric Order Management:**
  - Simplifies the order process with order review and default address selection features.

## Conclusion

This project showcases an end-to-end eCommerce grocery platform developed with a robust microservices architecture and modern technologies. The platform is designed to be secure, scalable, and user-friendly. Future iterations aim to integrate additional services, such as payment processing, to provide a complete eCommerce experience.

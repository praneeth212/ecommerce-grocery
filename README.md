# Ecommerce Grocery Platform

## Overview

This project is a full-stack ecommerce application developed using Java Spring Boot for the backend, React for the frontend, and MySQL for the database. It is designed as a monolithic application with functionalities including user management (Users and Admin), product catalog, cart, and orders management. Notably, it does not include a payment gateway or any third-party integrations.

## Features

- **User Authentication and Authorization**:
  - Implemented JWT token-based authentication and authorization.
  - Supports both customer and admin roles with distinct privileges.
  - Customers can add products to cart and place orders.
  - Admins have privileges to manage products and view product catalog.

- **User Profile Management**:
  - Includes functionalities such as login, register, and forgot password.
  - Users can edit their profiles and manage addresses.
  - Option to set a default address for convenience.

- **Admin Functionality**:
  - Admins can add new products via the backend and view them in the frontend.
  - Manages the product catalog visible to customers.

- **Cart Functionality**:
  - Allows users to add products to their cart for future purchase.
  - Provides standard cart operations such as adding, removing, and updating items.

- **Orders Management**:
  - Facilitates placing and managing orders for customers.
  - Includes functionalities for order history and status tracking.

## Project Structure

This application follows a basic ecommerce model with a focus on core functionalities essential for online shopping. It serves as a foundational project integrating backend, frontend, and database technologies.

## Technologies Used

- **Backend**: Java Spring Boot
- **Frontend**: React
- **Database**: MySQL

## Usage

To run the project locally:

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/ecommerce-grocery.git
   cd ecommerce-grocery

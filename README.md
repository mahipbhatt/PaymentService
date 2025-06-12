# PaymentService

## Overview
The `PaymentService` project is a Spring Boot application designed to handle payment processing using multiple payment gateways, including Stripe and Razorpay. It provides functionality to generate payment links, manage transactions, and integrate with external payment APIs.

## Features
- **Stripe Integration**: Generate payment links and manage payments using the Stripe API.
- **Razorpay Integration**: Configure and use Razorpay for payment processing.
- **Spring Boot Framework**: Built using Spring Boot for rapid development and scalability.
- **Database Support**: Uses JPA for database interactions with MySQL as the default database.
- **Actuator**: Provides monitoring and management endpoints for the application.

## Prerequisites
- **Java**: Version 17 or higher.
- **Maven**: Version 3.6 or higher.
- **MySQL**: Ensure a running MySQL instance for database connectivity.
- **Stripe API Key**: Obtain from the [Stripe Dashboard](https://dashboard.stripe.com/).
- **Razorpay API Key and Secret**: Obtain from the [Razorpay Dashboard](https://dashboard.razorpay.com/).

## Getting Started

### Clone the Repository
```bash
git clone -b capstoneProject <repository-url>
cd PaymentService
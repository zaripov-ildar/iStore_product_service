## Product Server

This project implements a service of orders and goods using Spring Boot 3.0 and integration with other servers using webflux.  It includes the following features:

### Features
* Search for products according to the filter according to the following criteria:
  > - minimum price
  > - maximum price
  > - part of the product name
  > - part of the category name
* Password encryption using BCrypt
* Role & Authorities -based authorization with Spring Security
* Customized access denied handling
* Refresh token
* Objects covered by tests for 65% and for 100% of the remaining classes

### Technologies
* Spring Boot 3.0.6
* Spring Security
* JSON Web Tokens (JWT)
* BCrypt
* Maven

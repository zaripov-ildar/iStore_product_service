## Product Server

This project implements a service of orders and goods using Spring Boot 3.0 and integration with other servers using webflux.  It includes the following features:

### Features
* Search for products by a combination of the following filters:

      - maximum price
    
      - minimum price
    
      - part of the product name
     
      - part of the category name

  
* Pagination
* Search product by id
* Products creating and editing
* Deleting product by id
* Orders creating
* Getting all user orders
* All endpoints protected by <a href="">gateway server</a> according users roles and authorities
* Project covered by tests for 78% lines and 97% of the remaining classes

### Technologies
* Spring Boot 3.0.6
* Spring-boot-starter-webflux for integration with <a href="">cart server</a>
* Postgres as a main database
* H2 as a database for test
* Maven

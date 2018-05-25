# Adding tenants without application restart in SaaS style multi-tenant web app with Spring Boot 2 and Spring Security 5
SaaS application style multi-tenancy with database per tenant using Spring Boot 2 + JPA + Hibernate + Spring Security 5. This app
is built with MySQL as the database. It can be adapted to use any other database like Microsoft SQL Server.

**Note:** This app reads the tenant information from a separate database table and does not require an application restart when new tenants are added.

This repository contains code which accompanies the blog post [Adding tenants without application restart in SaaS style multi-tenant web app
with Spring Boot 2 and Spring Security 5](https://sunitkatkar.blogspot.com/2018/05/adding-tenants-without-application.html)

## Getting Started

This is a typical maven project. Download the source as a zip file or checkout the code 
and import as an Existing Maven project in your IDE.

### Prerequisites

* Java 8
* Spring Boot 2
* MySQL
* Not mandatory, but you can use any suitable IDE like Spring STS


## Authors

* **Sunit Katkar** - *Initial work* - [Sunit Katkar](https://sunitkatkar.blogspot.com/)



## License

This project is licensed under the Apache License - see the [LICENSE.md](LICENSE.md) file for details

## Request
You are free to fork this repository, but please drop me a note at sunitkatkar@gmail.com

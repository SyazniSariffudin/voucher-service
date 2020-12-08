## What you'll build
- A simple Spring Boot application with MySQL running inside Docker containers

## What you'll need
- Docker CE

## Stack
- Docker
- Java
- Spring Boot and Spring Data
- MySQL
- Maven
- Executor and Runnable
- OpenApi
- Junit and Mockito

## Steps to run Voucher service with Mysql

1. **Clone the application**

	```bash
	gh repo clone SyazniSariffudin/voucher-service
	```

2. **Run docker image - Mysql**
    
	```bash
	docker-compose up
	```
 
 2. **Run Voucher Service**
     
 	```bash
 	mvn clean spring-boot:run
 	```
 
## Access API(s)
1. **Postman File**

	```bash
	voucher-api.postman_collection.json
	```

2. **Swagger**

	```bash
	http://localhost:8080/swagger-ui.html 
	```
# MedlAI - Backend
Main purpose of this part of the project is providing frontend with endpoints for interacting with database.

Also, it serves purpose as a mailing service for the appointment management.
## Technology Stack
- Spring Boot 
- Spring Boot Mail - sending mails through Google SMTP
- JWT - creating secure temporary links for appointment management 
- OpenAPI - creating endpoint documentation that is available in the endpoints.yaml file
- H2 Database

## Things that need to be provided in .env.properties file:
- MAIL_USERNAME 
- MAIL_PASSWORD 
- DB_PASSWORD
- JWT_TOKEN

## Directory Structure:
- configure - configuration files for JWT and CORS 
- controller - files with endpoints for communicating with frontend
- service - layer between controller and dao
- dao - interacting with database
- dto - objects that are being send to frontend
- model - defining database objects

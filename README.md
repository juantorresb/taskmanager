# taskmanager
This project allow to the user register and login in the application using spring-security and exposed a REST endpoint to manage tasks and Users 

Projected developed using the next tecnologies;
* Java / REST API 
* Springboot / Springsecutiry
* JWToken
* Swagger
* Lombok
* Maven
* H2 Database


![auth](https://github.com/juantorresb/taskmanager/assets/5738179/be0591c9-a36f-495c-8632-47b9676876f9)

Allow to the user to login, once is authorized the endpoint generates the token authentication to allow to interact with the others endpoints.
* /api/v1/auth/authenticate
* /api/v1/auth/logout
* /api/v1/tasks
* /api/v1/users

1. Once the application is started, automatically is create 3 user, you can find the token generated in the logs in case you want to use it:
* admin@mail.com/password
* executor@mail.com/password
* auditor@mail.com/password
  
2. Change the password, use the PATCH endpoint to update it 
* /api/v1/users

Endpoints completed (you can reference to Authentication.postman_colletion.json to validate the Endpoints and requests, *REMEMBER update the Authorization Bearer Token* 
* The user can update te password.
* The user cannot create other users with "Admin" role
* Create task with Title. Description, Due Date.
* List the tasks assigned by user.
* Update a Task
* List all the Tasks
* List all the users (Forbidden issue)
* Logout

(TODO) Pending requirements
* Cannot delete or update a task with a different "Assigned" status
* The user cannot update If the task is already overdue
* Add comments to Task


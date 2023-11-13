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


Allow to the user register and login, once is authorized the endpoint generates the token authentication to allow to interact with the others endpoints.
* /api/v1/auth
* /api/v1/tasks
* /api/v1/users

Done 
* The user can update te password.
* The user cannot create other users with "Admin" role
* Create task with Title. Description, Due Date.
* List the tasks assigned by user.
* Update a Task
* List all the Tasks
* List all the users

(TODO) Pending requirements
* Cannot delete or update a task with a different "Assigned" status
* The user cannot update If the task is already overdue
* Add comments to Task


 What has been done:
 -
 1. Poll-app - REST application based on Spring boot framework(Spring Data,Spring Security,Spring mvc).
   
 1. Database - H2 (with users,roles,and domain objects)
 1. Authentication - Basic Auth
    admin and user are in the database
    - admin
        - username : adimin
        - password : admin
    - user
        - username : user
        - password : admin    
 1. Admin functionality 
 
    Polls - Add/Update/DELETE
    - Once created, the "start date" field for the poll cannot be changed
    
    Questions - Add/Update/DELETE
 
 1. User functionality
    - user authentication
    - anonymous user maintenance(by userId parameter in request)
    - completing the polls 
    - getting poll statistic with detailed information for user
    - getting active polls
    
    
API documentation by Swagger 
-

Lunch application. API documentation will be on http://localhost:8080/swagger-ui/index.html

- at docs\CreatePollRequestBody.json - example of json
- at docs\Fabrique.postman_collection.json - request collection for postman


Compile and run instruction
-

- Run from Intellij IDEA.
    - In Intellij IDEA click to project folder->add framework support->Maven.
    - Lunch src\main\java\com\berdibekov\PollApplication.java .

- From Console 
    - Windows
        - run make.bat
        - run scripts\poll-app.bat
    - UNIX (Linux,Mac OS)     
        - chmod ugo+x make.sh 
        - make.sh (UNIX)
        - chmod ugo+x scripts\poll-app.sh
        - scripts\poll-app.sh (UNIX)
 
    
    
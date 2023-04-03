# Order Management Service

This is a Demo OMS application. The App allows user to perform basic CRUD operations on Order entities. The entities are
stored in MongoDB backend.

# Running the App locally

## MongoDB

The App expects MongoDB instance running on localhost and listening for incoming requests on port 27017. The App
codebase comes with a docker-compose file to start a docker container on localhost. Use the below docker-compose command
to bring up the MongoDB docker container on localhost. The file is located the application root folder.
```
docker-compose -f docker-compose-mongo-only.yml up
```

## Here is the command to bring up the App on localhost
```
./gradlew clean bootRun  
```

# How to access the App

The App doesn't have a UI at this time. It exposes 3 RESTful Apis to create an Order, fetch an Order and list all Order
entities in the system. Swagger Documentation UI has been added to the App to ease access to the Apis.

http://localhost:8080/swagger-doc/swagger-ui.html



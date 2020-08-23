# JAX-RS REST API - SOA Assignment 1 Summer Term 2020

Implement a beverage store as specified in the assignment description.
Your project must be buildable with gradle (the easiest way to achieve this is using this template).
If your project can't be run with `gradlew run` to start the JAX-RS server, your submission will be marked with 0 points.
The server is available at `localhost:9999/v1` per default. 
This can be configured via `src/main/resources/config.properties`.
 
To discover the different resources, methods and data schemas use the [Swagger Editor](https://editor.swagger.io/#) and the `openapi.yaml` file.
Also include a swagger UI resource to enable displaying swagger UI as in our demo project.

## Artefacts, you have to submit
- Source Code
- openapi.yaml
- insomnia.json

Import the Gradle project via `build.gradle` inside your preferred IDE. 

1. Resource implementations are located inside `de.uniba.dsg.jaxrs.resources`.
2. The shared model and entity classes for web services are located inside `de.uniba.dsg.jaxrs.models`.  
3. The service classes for beverages, customer and employee is in de.uniba.dsg.jaxrs.controller to segreggate business logic and database implementation.
4. BeverageService\src\main\resources folder contains config.properties, log4j.properties and openapi.yaml.
5. Location of all project level log generated is in src\logs\application.log

To Start the JAXRS server via 
`de.uniba.dsg.jaxrs.JaxRsServer.java`

To test the application and its services:
http://localhost:9999/v1/swagger-ui/index.html?url=openapi.yaml

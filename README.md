# Overview

A Java Spring Boot web-server that exposes APIs for Creating documents / Indexes on an ElasticSearch instance.

Java Version = 18
Spring Boot Version = 3.0

# Instructions

### Run the web server:

You can choose one of the following:

`maven:`

execute ```mvn spring-boot:run``` inside project's root folder
Will start the server and listen to requests on 8080 port.

`binary files:`

execute ```java -jar target/Vicarius2-0.0.1-SNAPSHOT.jar``` inside project's root folder

Both commands will launch the app.
### Run Tests
execute ```mvn test``` inside project's root folder. 
* make sure app is running beforehand



## Extra Notes:

* The program can be easily split to more packages / files depending on it's scale of growth, 
it is currently minimal, but also easily extensible.

* Program is concurrent in the sense that every new request arriving is being delegated to a separate Thread pool held by the app
 This helps avoid starvation from the underlying webserver of spring boot, thus prioritizing user requests and ability to serve them.

* App consists of layers: Routes, Handlers, Service, Data Access.


## Possible Improvements

* Depending on the coding-standard of the project / company - documentations can be added
* Add More tests can be conducted. The APIs are (somewhat) covered, but we can introduce tests for the internal code
* We can then mock those internal modules to test flows with mocked values
* Add logging
* Handle edge cases
* Add user authentication / authorization
* Create a Dockerfile

---

###### * Thanks for taking the time to read & assess the task!

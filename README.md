# Overview

A Java Spring Boot web-server that exposes APIs for Creating documents / Indexes on an ElasticSearch instance.

Java Version = 18
Spring Boot Version = 3.0

# Instructions

### Run the web server:

```java -jar target/Vicarius2-0.0.1-SNAPSHOT.jar```

Will start the server and listen to requests on 8080 port.


## Extra Notes:

* The program can be easily split to more packages / files depending on it's scale of growth, 
it is currently minimal, but also easily extensible. I.e a developer can easily create a new  directory for a specific handler and it's dependencies / utility code, whereas now, since handlers are quite minimal, there's only 1 handlers.go file that contains all handlers.

  
## Possible Improvements

* Depending on the coding-standard of the project / company - documentations can be added
* Add More tests can be conducted. The APIs are (somewhat) covered but we can introduce tests for the internal code
* We can then mock those internal modules to test flows with mocked values
* Add logging
* Create a _Dockerfile_

---

###### * Thanks for taking the time to read & assess the task!
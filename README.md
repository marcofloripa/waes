# Json Encoded Comparison API
Api responsible for analyse json encoded content

### Prerequisites

Java (Jdk v8)
Maven

### Install and running the app

First clone the project, after build the project and run the executable jar

```sh
$ git clone https://github.com/marcofloripa/waes.git
$ cd waes
$ mvn package
$ java -jar target/waes-0.0.1-SNAPSHOT.jar 
```

You can run using maven

```sh
$ mvn spring-boot:run 
```

## Database

The application will create two databases on the user home in the h2 folder:
* waes.mv.db
* waes_test.mv.db 

You can acces the h2 database on the browser: http://localhost:8080/h2-console

## Rest API

The documentation are located in: http://localhost:8080/swagger-ui.html

### TODO
* Docker

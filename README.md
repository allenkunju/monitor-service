# Flightright Group challenge #1

# Usage Monitoring Service

This spring boot application counts users visited our web-page from different sources.  
The application can filter out duplicate entries.
A unique user is identified via unique phone and email combination.

The input is a csv file, that contains:

```
email,phone,source
test@test.com,123,google.com
test@test.com,,google.com
test1@test.com,321,google.com
```


### Running Application
- We can build, test and run the application using maven.

Build 
- windows : `./mvnw compile`
- Linux : `./mvn compile`

Test 
- windows : `./mvnw verify`
- Linux : `/mvn verify`

Run
- windows : `./mvnw spring-boot:run`
- Linux : `./mvn spring-boot:run`

### Usage
- The functionality is exposed via a REST API

REST API can be invoked from any REST clients using url:  
    "http://localhost:8080/monitor/csv-report?filename=sample_100.csv" 

Alternatively, the application can be accessed via a browser.
1. Once the application is run, open "http://localhost:8080/" on any browser.
2. provide the file name "sample_100.csv"
3. On Submit, the result is shown on the same page.

Assumptions
-
1. The input files are stored in the 'src/resources/files' folder. 
If required, The functionality can be extended to pass the file via a POST request.
2. Output is written to another csv file with in the filesystem.
3. In-memory cache is used to store the unique entries to check the duplicates. It can be moved to an external cache for huge data.
4. The application is run locally on port 8080.
5. Integration and unit tests are available.
6. Front-end has basic functionality to test the API. It's written in basic javascript.
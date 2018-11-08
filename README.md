# Base 64 encoded string comparator

This is simple standalone web application, developed to compare base 64 encoded strings.

## Requirements -

Provide 2 http endpoints that accepts JSON base64 encoded binary data on both
endpoints
o <host>/v1/diff/<ID>/left and <host>/v1/diff/<ID>/right
• The provided data needs to be diff-ed and the results shall be available on a third end
point
o <host>/v1/diff/<ID>
• The results shall provide the following info in JSON format
o If equal return that
o If not of equal size just return that
o If of same size provide insight in where the diffs are, actual diffs are not needed.
§ So mainly offsets + length in the data

## Technology stack

This project is developed on Java 8 and maven 4.
This project is built upon spring boot which provides easy setup and integration with tomcat and database (H2). The technology also written with boilerplate which enables developers to concentrate on business implementation.

```Springboot Web - Web endpoints and embedded tomcat container
Springboot JPA - integrate with DB
H2 database - datastore
Apache common - Utility methods
Springboot test - integration test
jUnit & Mockito - mock based unit testing
```
## Features
This project provides 2 http endpoints (`<host>/v1/diff/<ID>/left` and `<host>/v1/diff/<ID>/right`) that accept JSON containing base64 encoded binary data on both endpoints.
The provided data is compared and result is shown on the third endpoint `<host>/v1/diff/<ID>`.

## Sample request / response

| Request | Response |
| ---  | --- |
| POST /v1/diff/1/left {"data": "dGhpcyBpcyB3YWVzIHRlc3QgcHJvZ3JhbQ=="}	 | 201 Created  |
| POST /v1/diff/1/right {"data": "dGhpcyBpcyB3YWVzIHRlc3QgcHJvZ3JhbQ=="}	 | 201 Created  |
| GET /v1/diff/1 |200 OK {"comparatorResult":"EQUAL","contentDifferences":null}        |
| ---  | --- |
| POST /v1/diff/2/left {"data": "dGhpcyBpcyB3YWVzIHRlc3QgcHJvZ3JhbQ=="}	 | 201 Created  |
| POST /v1/diff/2/right {"data": "VGhpcyBpcyB3cm9uZyBpbnB1dA=="}	 | 201 Created  |
| GET /v1/diff/2 |200 OK {"comparatorResult":"LENGTH_MISMATCH","contentDifferences":null}        |
| ---  | --- |
| POST /v1/diff/3/left {"data": "ZGl3YWxp"}	 | 201 Created  |
| POST /v1/diff/3/right {"data": "ZGl3YWxh"}	 | 201 Created  |
| GET /v1/diff/3 |200 OK {"comparatorResult":"OFFSET_MISMATCH","contentDifferences":[{"offset":7,"length":1}]}        |
| ---  | --- |
| Exception  | Scenarios |
| ---  | --- |
| GET /v1/diff/4 |404 NOT FOUND ("Record not present for id - 4")          |
| ---  | --- |
| POST /v1/diff/5/left {"data": "dGhpcyBpcyB3YWVzIHRlc3QgcHJvZ3JhbQ=="}	 | 201 Created  |
| GET /v1/diff/5 |404 NOT FOUND ("Data not sufficient to perform comparison")          |
| ---  | --- |
| POST /v1/diff/6/left {"data": "ZGl3YWxp="}	 | 422 Unprocessable Entity (Data is not base 64 encoded format)  |

## Unit testing
Unit test cases are written under src\test\java\com\waes\techinterview\comparator\unit. <BR/>
3 different test classes viz -
```
ComparatorServiceTest -> comparator scenarios - 11 test cases
H2DBStorageServiceMockTest -> Mock based storage service test scnarios - 4 test cases
ValidationServiceTest -> Input data validation scenarios - 4 test cases
```

## Integration testing
Integration test cases are written under class -src\test\java\com\waes\techinterview\comparator\integration.<BR/>
Used springboot test package to test request - response - 10 test cases.


## Usage
To compile, build and generate jar -
`mvn package`

To test -
`mvn test`

To run -
```
mvn package
java -jar target/comparator-0.0.1-SNAPSHOT.jar
```

### Future changes
1. Dozer mapper to map entity and pojo, which enables no code writing for mapping
2. support for File import (text)

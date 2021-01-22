# Library

## Project Definition 

### Definition
 What this software that uses 


// TODO (Add here how to start/run project) and how StartUpService.java works
/ TODO add running guide -> download and unpack, then run gradlew build ensure that startupservice is enabled in applicaiton.properties
## Running the application

## Entities

### User

User is a person that uses the Library through Activities on Books

### Book

Book is an object that can be borrowed by user for a limited amount of time

### Library

A location where books are stored

### Activity

An action done by a user on a book

Activity An activity done by a user action enum - CheckedIn - CheckedOut - Reserved - Date

## Endpoints

### UserController

- Create user

  `POST /createUser requestBody: CreateUserRequest.java`

- Get the list of users

  `GET /user`

- Get the favourite book of user for a specific month of a year or just year
  - Note: Favourite means a
  book they "interacted" the most, in our system this would be the calculated as the sum of
  activities

  `GET /getFavouriteBook requestParameters: [userId, month, year]`

- On average how long does a user keep a book

  `GET /averageHoldOnBook requestPrameters: [userId]`

- Get top 10 most active users

  `GET /top10Users`

### BookController

- Get most borrowed book in a ibrary in a given month of a year, list limits the amount returned

  `GET /mostBorrowedBook requestParameters: [ibraryId, month, year, list]`

- Get most reserved book in a ibrary in a given month of a year, list limits the amount returned

  `GET /mostBorrowedBook requestParameters: [ibraryId, month, year, list]`

#### ActivityController

-GET  Least/Most Active User(s)

## Elasticsearch and Kibana Docs

### Elasticsearch

- Version in project: _7.10_
- [Installation Guide](https://www.elastic.co/guide/en/elasticsearch/reference/current/install-elasticsearch.html)
- [Java Rest Client Docs](https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/index.html)
- Default Elasticsearch Port: **9200**
- Starting Elasticsearch in a Windows machine installed in Program
  Files: `.\'Program Files'\elasticsearch-7.10.1\bin\elasticsearch.bat`
- Adding new instance: `.\elasticsearch.bat -E path.data=[path.data] -E path.logs=[path.log]`

### Kibana

- Version in project: _7.10_
- [Installation](https://www.elastic.co/guide/en/kibana/current/install.html)
- Starting Elasticsearch in a Windows machine installed in Program
  Files: `.\'Program Files'\kibana-7.10.1-windows-x86_64\bin\kibana.bat`
- Default Kibana Port: **5601**

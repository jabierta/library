# Spring Data Elasticsearch

# Starting elasticsearch in my local: .\'Program Files'\elasticsearch-7.10.1\bin\elasticsearch.bat

# Default Elasticsearch PORT: 9200

# Adding new instance: .\elasticsearch.bat -E path.data=[path.data] -E path.logs=[path.log]

# Starting Kibana in my local: .\'Program Files'\kibana-7.10.1-windows-x86_64\bin\kibana.bat

# Default Kibana PORT: 5601
    
# What is the project 
    Libariries in province
    Users
    Activity
    Books
    Libraries

 Current Page: https://docs.spring.io/spring-data/elasticsearch/docs/4.1.2/reference/html/#elasticsearch.clients
Goals:
 1. Show advance aggregates and qurries


Controllers all will have basic CRUD operations:
    User

    Book

    Library
        Basically just a location that contains books

    Activity
        An activity done by a user action enum
        - CheckedIn
        - CheckedOut
        - Inquired
        - Reserved
        - Date  

Explain Project

Explain Setup

Explain Usage
    - Start up
    - Endpoints
        
        - Get activities of user
        - Get favourite book of user
        
        - Get the most popular book borrowed
        


https://stackoverflow.com/questions/24778454/how-to-insert-multiple-records-in-one-query/24778541

https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-overview.html
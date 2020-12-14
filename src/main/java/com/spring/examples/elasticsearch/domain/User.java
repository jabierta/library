package com.spring.examples.elasticsearch.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "user")
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
}

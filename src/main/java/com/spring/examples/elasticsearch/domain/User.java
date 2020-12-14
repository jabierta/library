package com.spring.examples.elasticsearch.domain;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "user")
public class User {
    private String firstName;
    private String lastName;
}

package com.spring.examples.elasticsearch.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "library")
public class Library {
  @Id private String id;
  private String name;
  private String street;
  private String city;
  private String province;
}

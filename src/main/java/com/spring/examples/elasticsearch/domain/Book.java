package com.spring.examples.elasticsearch.domain;

import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "book")
public class Book {
  @Id private String id;
  private String title;
  private String author;
  private Date year;
  private Boolean isAvailableToBorrow;
  private Date nextAvailability;
  private String libraryId;
}

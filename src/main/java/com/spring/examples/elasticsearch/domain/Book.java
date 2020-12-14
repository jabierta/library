package com.spring.examples.elasticsearch.domain;

import java.util.Date;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "book")
public class Book {
  private String title;
  private String author;
  private String ISBN;
  private Date year;
  private Boolean isAvailableToBorrow;
  private Date nextAvailability;
}

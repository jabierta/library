package com.spring.examples.elasticsearch.domain;

import java.util.Date;
import lombok.Data;

@Data
public class BookRecord {
  private String bookId;
  private Date returnDate;
}

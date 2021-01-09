package com.spring.examples.elasticsearch.domain;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRecord {
  private String bookId;
  private Date returnDate;
}

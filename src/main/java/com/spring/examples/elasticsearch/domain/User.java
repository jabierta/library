package com.spring.examples.elasticsearch.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "user")
public class User {
  @Id private String id;
  private String firstName;
  private String lastName;
  private List<BookRecord> currentlyBorrowedBooks;
  private List<BookRecord> currentlyReservedBooks;
}

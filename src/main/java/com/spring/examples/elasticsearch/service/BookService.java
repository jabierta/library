package com.spring.examples.elasticsearch.service;

import com.spring.examples.elasticsearch.domain.Book;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
  private final RestHighLevelClient elasticSearchClient;

  public Book find(String bookId) {
    try {
      GetResponse getResponse =
          elasticSearchClient.get(new GetRequest("book", bookId), RequestOptions.DEFAULT);
      return sourceAsMapToBook(bookId, getResponse.getSourceAsMap());
    } catch (IOException e) {
      return null;
    }
  }

  public Book sourceAsMapToBook(String bookId, Map<String, Object> sourceAsMap) {
    Book book = new Book();
    book.setId(bookId);
    book.setAuthor((String) sourceAsMap.get("author"));
    book.setYear((Date) sourceAsMap.get("year"));
    book.setIsAvailableToBorrow((Boolean) sourceAsMap.get("isAvailableToBorrow"));
    book.setNextAvailability((Date) sourceAsMap.get("nextAvailability"));
    book.setLibraryId((String) sourceAsMap.get("libraryId"));

    return book;
  }
}

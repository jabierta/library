package com.spring.examples.elasticsearch.service;

import com.spring.examples.elasticsearch.domain.Book;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    } catch (IOException | ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Book sourceAsMapToBook(String bookId, Map<String, Object> sourceAsMap)
      throws ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    Book book = new Book();
    book.setId(bookId);
    book.setAuthor((String) sourceAsMap.get("author"));
    book.setYear(simpleDateFormat.parse((String) sourceAsMap.get("year")));
    book.setIsAvailableToBorrow((Boolean) sourceAsMap.get("isAvailableToBorrow"));
    book.setNextAvailability(simpleDateFormat.parse((String) sourceAsMap.get("nextAvailability")));
    book.setLibraryId((String) sourceAsMap.get("libraryId"));

    return book;
  }
}

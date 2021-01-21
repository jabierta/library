package com.spring.examples.elasticsearch.service;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

import com.spring.examples.elasticsearch.domain.BookRecord;
import com.spring.examples.elasticsearch.domain.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

@Service
@RequiredArgsConstructor
public class UserService {
  private final ElasticsearchOperations elasticsearchOperations;
  private final RestHighLevelClient elasticsearchClient;

  public User create(String firstName, String lastName) {
    User user = new User();
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setCurrentlyBorrowedBooks(new ArrayList<BookRecord>());
    user.setCurrentlyReservedBooks(new ArrayList<BookRecord>());
    try {
      return elasticsearchOperations.save(user);

    } catch (Exception e) {
      e.printStackTrace();
      throw new ServerErrorException("Server encountered an error creating user.", e);
    }
  }

  public User findUserById(String id) {
    return elasticsearchOperations.get(id, User.class);
  }

  public List<User> list() {
    List<User> users = new ArrayList<>();

    SearchRequest searchRequest = new SearchRequest("user");
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(matchAllQuery());
    searchSourceBuilder.size(10000);
    searchRequest.source(searchSourceBuilder);
    searchRequest.scroll(TimeValue.timeValueMinutes(1L));
    SearchResponse searchResponse = null;
    String scrollId = null;
    try {
      searchResponse = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
      scrollId = searchResponse.getScrollId();

      while (searchResponse.getHits() != null && searchResponse.getHits().getHits().length > 0) {
        SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
        scrollRequest.scroll(TimeValue.timeValueMinutes(1L));

        users.addAll(
            Arrays.stream(searchResponse.getHits().getHits())
                .map(
                    hit ->
                        new User(
                            hit.getId(),
                            (String) hit.getSourceAsMap().get("firstName"),
                            (String) hit.getSourceAsMap().get("lastName"),
                            this.toBookRecord(
                                (List<HashMap<String, Object>>)
                                    hit.getSourceAsMap().get("currentlyBorrowedBooks")),
                            this.toBookRecord(
                                (List<HashMap<String, Object>>)
                                    hit.getSourceAsMap().get("currentlyReservedBooks"))))
                .collect(Collectors.toList()));

        searchResponse = elasticsearchClient.scroll(scrollRequest, RequestOptions.DEFAULT);
        scrollId = searchResponse.getScrollId();
      }

      ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
      clearScrollRequest.addScrollId(scrollId);
      ClearScrollResponse clearScrollResponse =
          elasticsearchClient.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
    } catch (IOException e) {
      e.printStackTrace();
      throw new ServerErrorException("Server encountered an error listing users.", e);
    }

    return users;
  }

  public List<User> getUserByFirstName(String firstName) {
    return getByPropertyValue("firstName", firstName);
  }

  public List<User> getUserByLastName(String lastName) {
    return getByPropertyValue("lastName", lastName);
  }

  private List<User> getByPropertyValue(String field, String fieldValue) {
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.matchQuery(field + ".keyword", fieldValue));

    SearchRequest searchRequest = new SearchRequest();
    searchRequest.source(searchSourceBuilder);

    SearchResponse searchResponse = null;
    try {
      searchResponse = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);

      return Arrays.stream(searchResponse.getHits().getHits())
          .map(
              hit ->
                  new User(
                      hit.getId(),
                      (String) hit.getSourceAsMap().get("firstName"),
                      (String) hit.getSourceAsMap().get("lastName"),
                      this.toBookRecord(
                          (List<HashMap<String, Object>>)
                              hit.getSourceAsMap().get("currentlyBorrowedBooks")),
                      this.toBookRecord(
                          (List<HashMap<String, Object>>)
                              hit.getSourceAsMap().get("currentlyReservedBooks"))))
          .collect(Collectors.toList());

    } catch (IOException e) {
      e.printStackTrace();
      throw new ServerErrorException("Server encountered an error finding users.", e);
    }
  }

  private User update(String id, String firstName, String lastName) {
    Map<String, Object> jsonMap = new HashMap<>();
    jsonMap.put("firstName", firstName);
    jsonMap.put("lastName", lastName);

    try {
      GetResult result =
          elasticsearchClient
              .update(new UpdateRequest("user", id).doc(jsonMap), RequestOptions.DEFAULT)
              .getGetResult();
      Map<String, Object> sourceAsMap = result.sourceAsMap();
      return new User(
          result.getId(),
          (String) sourceAsMap.get("firstName"),
          (String) sourceAsMap.get("lastName"),
          this.toBookRecord(
              (List<HashMap<String, Object>>) sourceAsMap.get("currentlyBorrowedBooks")),
          this.toBookRecord(
              (List<HashMap<String, Object>>) sourceAsMap.get("currentlyReservedBooks")));

    } catch (IOException e) {
      e.printStackTrace();
      throw new ServerErrorException("Server encountered an error updating user.", e);
    }
  }

  private void delete(String id) {
    try {
      elasticsearchClient.delete(new DeleteRequest("user", id), RequestOptions.DEFAULT);
    } catch (IOException e) {
      e.printStackTrace();
      throw new ServerErrorException("Server encountered an error deleting user.", e);
    }
  }

  public List<BookRecord> toBookRecord(List<HashMap<String, Object>> hashMapList) {
    List<BookRecord> bookRecords = new ArrayList<BookRecord>();
    if (hashMapList != null && !hashMapList.isEmpty()) {
      for (HashMap<String, Object> hashMap : hashMapList) {
        BookRecord bookRecord = new BookRecord();
        bookRecord.setBookId((String) hashMap.get("bookId"));
        bookRecord.setReturnDate((Long) hashMap.get("returnDate"));
        bookRecords.add(bookRecord);
      }
    }

    return bookRecords;
  }
}

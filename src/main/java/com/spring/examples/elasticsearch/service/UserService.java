package com.spring.examples.elasticsearch.service;

import com.spring.examples.elasticsearch.domain.User;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
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

  // CRUD operations
  public User create(String firstName, String lastName) {
    User user = new User();
    user.setFirstName(firstName);
    user.setLastName(lastName);
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
                      (String) hit.getSourceAsMap().get("lastName")))
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
          (String) sourceAsMap.get("lastName"));

    } catch (IOException e) {
      e.printStackTrace();
      throw new ServerErrorException("Server encountered an error updating user.", e);
    }
  }

  private void delete(String id) {}
}

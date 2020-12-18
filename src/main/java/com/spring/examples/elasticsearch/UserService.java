package com.spring.examples.elasticsearch;

import com.spring.examples.elasticsearch.domain.User;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
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
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.matchQuery("firstName.keyword", "firstName"));

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

  public User getUserByLastName() {
    return null;
  }
}

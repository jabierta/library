package com.spring.examples.elasticsearch.service;

import java.io.IOException;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StartUpService {
  private final RestHighLevelClient elasticsearchClient;

  @PostConstruct
  private void initializeData() throws IOException {
    if (!elasticsearchClient
        .indices()
        .exists(new GetIndexRequest("user"), RequestOptions.DEFAULT)) {
      CreateIndexRequest request = new CreateIndexRequest("user");
    } else {
      elasticsearchClient.deleteByQuery(
          new DeleteByQueryRequest("user").setQuery(QueryBuilders.matchAllQuery()),
          RequestOptions.DEFAULT);

      BulkRequest request = new BulkRequest();
      request.add(createUserRequest("John", "Doe"));
      request.add(createUserRequest("Jane", "Doe"));
      request.add(createUserRequest("Mark", "Doe"));
      request.add(createUserRequest("Mills", "Doe"));
      request.add(createUserRequest("Ian", "Deer"));
      request.add(createUserRequest("Anna", "Deer"));
      request.add(createUserRequest("Anthony", "Deer"));
      request.add(createUserRequest("Josh", "Smith"));
      request.add(createUserRequest("Jenny", "Smith"));
      request.add(createUserRequest("Jack", "Mason"));
      request.add(createUserRequest("Alex", "Mason"));
    }
    // if user index dne then create one, else delete all data and insert new data

    // if activity index then create one, else delete all data and insert new data

    // if library index then create one, else delete all data and insert new data

    // if book index then create one, else delete all data and insert new data
  }

  private IndexRequest createUserRequest(String firstName, String lastName) {
    return new IndexRequest("posts")
        .id("1")
        .source(XContentType.JSON, "firstName", firstName, "lastName", lastName);
  }
}

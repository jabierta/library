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
    BulkRequest bulkRequest = new BulkRequest();

    if (!elasticsearchClient
        .indices()
        .exists(new GetIndexRequest("user"), RequestOptions.DEFAULT)) {
      elasticsearchClient.indices().create(new CreateIndexRequest("user"), RequestOptions.DEFAULT);
    } else {
      elasticsearchClient.deleteByQuery(
          new DeleteByQueryRequest("user").setQuery(QueryBuilders.matchAllQuery()),
          RequestOptions.DEFAULT);
    }

    bulkRequest.add(createUserRequest("John", "Doe"));
    bulkRequest.add(createUserRequest("Jane", "Doe"));
    bulkRequest.add(createUserRequest("Mark", "Doe"));
    bulkRequest.add(createUserRequest("Mills", "Doe"));
    bulkRequest.add(createUserRequest("Ian", "Deer"));
    bulkRequest.add(createUserRequest("Anna", "Deer"));
    bulkRequest.add(createUserRequest("Anthony", "Deer"));
    bulkRequest.add(createUserRequest("Josh", "Smith"));
    bulkRequest.add(createUserRequest("Jenny", "Smith"));
    bulkRequest.add(createUserRequest("Jack", "Mason"));
    bulkRequest.add(createUserRequest("Alex", "Mason"));

    if (!elasticsearchClient
        .indices()
        .exists(new GetIndexRequest("library"), RequestOptions.DEFAULT)) {
      elasticsearchClient
          .indices()
          .create(new CreateIndexRequest("library"), RequestOptions.DEFAULT);
    } else {
      elasticsearchClient.deleteByQuery(
          new DeleteByQueryRequest("library").setQuery(QueryBuilders.matchAllQuery()),
          RequestOptions.DEFAULT);
    }

    bulkRequest.add(
        new IndexRequest("library")
            .source(
                XContentType.JSON,
                "name",
                "Abiertas Library",
                "street",
                "Awesome Street",
                "city",
                "Abiertas",
                "province",
                "AWE"));

    // if book index then create one, else delete all data and insert new data





    // if activity index then create one, else delete all data and insert new data

    elasticsearchClient.bulk(bulkRequest, RequestOptions.DEFAULT);
  }

  private IndexRequest createUserRequest(String firstName, String lastName) {
    return new IndexRequest("user")
        .source(XContentType.JSON, "firstName", firstName, "lastName", lastName);
  }
}

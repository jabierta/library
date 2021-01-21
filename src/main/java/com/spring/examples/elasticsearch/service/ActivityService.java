package com.spring.examples.elasticsearch.service;

import java.io.IOException;
import java.util.Calendar;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService {
  private final RestHighLevelClient elasticsearchClient;

  public String getFavouriteBook(String userId, Integer month, Integer year) {
    long startDate;
    long endDate;
    if (month == null) {
      Calendar calendar = Calendar.getInstance();
      calendar.set(year, Calendar.JANUARY, 1);
      startDate = calendar.getTimeInMillis();
      calendar.set(year, Calendar.DECEMBER, 31);
      endDate = calendar.getTimeInMillis();
    } else {
      int currentMonth = month - 1;
      Calendar calendar = Calendar.getInstance();
      calendar.set(year, currentMonth, 1);
      startDate = calendar.getTimeInMillis();
      calendar.set(year, currentMonth + 1, 1);
      endDate = calendar.getTimeInMillis();
    }

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(
        QueryBuilders.boolQuery()
            .must(QueryBuilders.matchQuery("userId", userId))
            .must(QueryBuilders.rangeQuery("activityDate").gte(startDate).lte(endDate)));
    searchSourceBuilder.aggregation(
        AggregationBuilders.terms("by_books")
            .field("bookId.keyword")
            .order(BucketOrder.count(false))
            .size(1));

    SearchRequest searchRequest = new SearchRequest();
    searchRequest.source(searchSourceBuilder);

    try {
      SearchResponse searchResponse =
          elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);

      Terms topBookAgg = searchResponse.getAggregations().get("by_books");

      return topBookAgg.getBuckets().get(0).getKeyAsString();
    } catch (IOException e) {
      return null;
    }
  }
}

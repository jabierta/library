package com.spring.examples.elasticsearch.service;

import com.spring.examples.elasticsearch.domain.Action;
import com.spring.examples.elasticsearch.domain.Activity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
      e.printStackTrace();
      return null;
    }
  }

  public double getAverageHoldOnBooksByUserId(String userId) {
    try {
      List<Integer> averageDurationForBooks = new ArrayList<>();
      // Query -> bool{must[{match:"userId"},{terms: activityDate:["CHCECKEDIN","CHECKEDOUT"]}]}
      SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

      searchSourceBuilder
          .query(
              QueryBuilders.boolQuery()
                  .must(QueryBuilders.matchQuery("userId.keyword", userId))
                  .must(
                      QueryBuilders.termsQuery(
                          "activityType.keyword",
                          Arrays.asList(Action.CHECKEDIN.name(), Action.CHECKEDOUT.name()))))
          .size(10000);

      searchSourceBuilder.aggregation(
          AggregationBuilders.terms("by_books")
              .field("bookId.keyword")
              .subAggregation(
                  AggregationBuilders.terms("by_activityType")
                      .field("activityType.keyword")
                      .subAggregation(
                          AggregationBuilders.terms("by_activityDate").field("activityDate"))));

      SearchRequest searchRequest = new SearchRequest();
      searchRequest.source(searchSourceBuilder);

      SearchResponse searchResponse =
          elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);

      Terms topBookAgg = searchResponse.getAggregations().get("by_books");

      Terms byBooks = searchResponse.getAggregations().get("by_books");

      byBooks.getBuckets().get(0).getAggregations()

      Map<String, List<Activity>> bookActivity = new HashMap<>();

      // for each element sort the activity date, this must be odd otherwise remove the last
      // element.
      // after get the duration between CHECKEDIN, CHECKEDOUT by grabbing two elements at a time
      // then find average of durations then insert to averageDurationForBooks;

      return averageDurationForBooks.stream().mapToDouble(element -> element).average().orElse(0);

    } catch (IOException e) {
      e.printStackTrace();
      return 0;
    }
  }
}

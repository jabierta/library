package com.spring.examples.elasticsearch.service;

import java.util.Calendar;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService {
  private final RestHighLevelClient elasticsearchClient;

  public String getFavouriteBook(String userId, Integer month, Integer year) {
    long startDate;
    long endDate;
    if (month == null) {
      int currentMonth = month - 1;
      Calendar calendar = Calendar.getInstance();
      calendar.set(year, 0);
      startDate = calendar.getTimeInMillis();
      calendar.set(year, 11);
      endDate = calendar.getTimeInMillis();
    } else {
      int currentMonth = month - 1;
      Calendar calendar = Calendar.getInstance();
      calendar.set(year, (month - 1));
      startDate = calendar.getTimeInMillis();
      calendar.set(year, month);
      endDate = calendar.getTimeInMillis();
    }

    return null;
  }
}

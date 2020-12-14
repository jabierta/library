package com.spring.examples.elasticsearch.domain;

import java.util.Date;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "activity")
public class Activity {
  private String activityType;
  private Date activityDate;
}

package com.spring.examples.elasticsearch.configuration;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class ElasticsearchClientConfiguration extends AbstractElasticsearchConfiguration {
  @Value("${elasticsearh.hostandport}")
  private String hostAndPort;

  @Override
  @Bean
  public RestHighLevelClient elasticsearchClient() {
    final ClientConfiguration clientConfiguration =
        ClientConfiguration.builder().connectedTo(hostAndPort).build();

    return RestClients.create(clientConfiguration).rest();
  }
}

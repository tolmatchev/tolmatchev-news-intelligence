package com.tolmatchev.newsintelligence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/** Configuration for RestClient. */
@Configuration
public class RestClientConfig {

  /**
   * Creates a RestClient.Builder.
   *
   * @return the RestClient.Builder.
   */
  @Bean
  public RestClient.Builder restClientBuilder() {
    return RestClient.builder();
  }

  /**
   * Creates a RestClient.
   *
   * @param builder the RestClient.Builder.
   * @return the RestClient.
   */
  @Bean
  public RestClient restClient(RestClient.Builder builder) {
    return builder.build();
  }
}

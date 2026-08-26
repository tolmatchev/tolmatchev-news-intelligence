package com.tolmatchev.newsintelligence.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tolmatchev.newsintelligence.dto.TassRssResponse;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
public class RssClient {
  private final XmlMapper xmlMapper;
  private final RestClient restClient;
  private final String rssUrl;

  public RssClient(RestClient restClient, @Value("${rss.url}") String rssUrl) {
    this.restClient = restClient;
    this.rssUrl = rssUrl;
    this.xmlMapper = XmlMapper.builder().addModule(new JavaTimeModule()).build();
  }

  public TassRssResponse fetchTassRss() {
    String xml =
        restClient
            .get()
            .uri(rssUrl)
            .header("Accept", "application/rss+xml")
            .header("Accept-Encoding", "gzip")
            .exchange(
                (request, response) -> {
                  log.info("Status: {}", response.getStatusCode());
                  log.info("Content-Type: {}", response.getHeaders().getFirst("Content-Type"));

                  return new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
                });

    try {
      return xmlMapper.readValue(xml, TassRssResponse.class);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Failed to parse TASS RSS", e);
    }
  }
}

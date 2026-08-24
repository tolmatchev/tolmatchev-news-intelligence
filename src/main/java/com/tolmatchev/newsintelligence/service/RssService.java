package com.tolmatchev.newsintelligence.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tolmatchev.newsintelligence.dto.RssResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import com.tolmatchev.newsintelligence.dto.ChannelDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class RssService {
    private final RestClient restClient;
    private final XmlMapper xmlMapper;

    public RssService(RestClient restClient) {
        this.restClient = restClient;
        this.xmlMapper = XmlMapper.builder().addModule(new JavaTimeModule())
                .build();
    }

    @Scheduled(fixedRate = 60000)
    public void scheduledFetchRss() {
        RssResponse data = fetchRss();
        log.info("Received RSS data: {}", data);
    }

    public RssResponse fetchRss() {
        String xml = restClient.get()
                .uri("https://tass.ru/rss/v2.xml")
                .header("Accept", "application/rss+xml")
                .header("Accept-Encoding", "gzip")
                .exchange((request, response) -> {
                    log.info("Status: {}", response.getStatusCode());
                    log.info("Content-Type: {}", response.getHeaders().getFirst("Content-Type"));

                    return new String(
                            response.getBody().readAllBytes(),
                            StandardCharsets.UTF_8
                    );
                });

        try {
            return xmlMapper.readValue(xml, RssResponse.class);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to parse TASS RSS", e);
        }

    }
}

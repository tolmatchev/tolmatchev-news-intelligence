package com.tolmatchev.newsintelligence.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import com.tolmatchev.newsintelligence.dto.ChannelDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class RssService {
    private final RestClient restClient;


    @Scheduled(fixedRate = 60000)
    public void scheduledFetchRss() {
        String data = fetchRss();
        log.info("Received RSS data: {}", data);
    }

    public String fetchRss() {
        return restClient.get()
                .uri("https://tass.ru/rss/v2.xml")
                .header("Accept", "*/*")
                .header("User-Agent", "curl/8.7.1")
                .header("Accept-Encoding", "gzip")
                .exchange((request, response) -> {
                    log.info("Status: {}", response.getStatusCode());
                    log.info("Content-Type: {}", response.getHeaders().getFirst("Content-Type"));

                    return new String(
                            response.getBody().readAllBytes(),
                            StandardCharsets.UTF_8
                    );
                });
    }

    private record RssResponse(ChannelDto channel) {}
}
